package com.monty.ui.profile

import com.monty.domain.behavior.LoadingCompletableBehavior
import com.monty.domain.profile.EditProfileCompletabler
import com.monty.domain.profile.GetProfileObservabler
import com.monty.domain.profile.SignInCompletabler
import com.monty.ui.base.SubmitButtonState
import com.monty.ui.profile.contract.*
import com.sumera.koreactor.behaviour.completable
import com.sumera.koreactor.behaviour.messages
import com.sumera.koreactor.behaviour.triggers
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import com.sumera.koreactor.util.extension.getChange
import com.sumera.koreactor.util.extension.getTrue
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProfileReactor @Inject constructor(
    private val getProfileObservabler: GetProfileObservabler,
    private val editProfileCompletabler: EditProfileCompletabler,
    private val signInCompletabler: SignInCompletabler
) : MviReactor<ProfileState>() {

    override fun createInitialState() = ProfileState.INITIAL

    override fun bind(actions: Observable<MviAction<ProfileState>>) {
        val onFirstNameChangeAction = actions.ofActionType<OnFirstNameChangeAction>()
        val onLastNameChangeAction = actions.ofActionType<OnLastNameChangeAction>()
        val onEmailChangeAction = actions.ofActionType<OnEmailChangeAction>()
        val onPhoneChangeAction = actions.ofActionType<OnPhoneChangeAction>()
        val onButtonAction = actions.ofActionType<OnButtonAction>()
        val onSignInAction = actions.ofActionType<OnSignInAction>()
        val onSignInStartAction = actions.ofActionType<OnSignInStartAction>()
        val onSignInSuccessAction = actions.ofActionType<OnSignInSuccessAction>()

        onFirstNameChangeAction.map { ChangeFirstNameReducer(it.firstName) }.bindToView()
        onLastNameChangeAction.map { ChangeLastNameReducer(it.lastName) }.bindToView()
        onEmailChangeAction.map { ChangeEmailReducer(it.email) }.bindToView()
        onPhoneChangeAction.map { ChangePhoneReducer(it.phone) }.bindToView()
        onSignInAction.map { SignInEvent }.bindToView()
        onSignInStartAction.map { ChangeSignInStateReducer(SubmitButtonState.PROGRESS) }
            .bindToView()

        stateObservable.getChange { it }
            .filter { it.buttonState != SubmitButtonState.PROGRESS && it.buttonState != SubmitButtonState.SUCCESS }
            .map { ChangeButtonStateReducer(isChangedProfile(it)) }
            .bindToView()

        attachLifecycleObservable
            .flatMap { getProfileObservabler.execute() }
            .map { ChangeProfileReducer(it) }
            .bindToView()

        LoadingCompletableBehavior<ProfileState, ProfileState>(
            triggers = triggers(onButtonAction.flatMapSingle { stateSingle }),
            worker = completable {
                editProfileCompletabler.init(
                    firstName = it.firstName,
                    lastName = it.lastName,
                    phone = it.phone,
                    email = it.email,
                    user = it.profile
                ).execute()
            },
            cancelPrevious = false,
            onStart = messages { ChangeButtonStateReducer(SubmitButtonState.PROGRESS) },
            onError = messages(
                { ChangeButtonStateReducer(SubmitButtonState.IDLE) },
                { ErrorEvent(it.message.toString()) }
            ),
            onComplete = messages { ChangeButtonStateReducer(SubmitButtonState.SUCCESS) }
        ).bindToView()

        LoadingCompletableBehavior<OnSignInSuccessAction, ProfileState>(
            triggers = triggers(onSignInSuccessAction),
            worker = completable { signInCompletabler.init(it.user).execute() },
            cancelPrevious = false,
            onStart = messages { ChangeSignInStateReducer(SubmitButtonState.PROGRESS) },
            onError = messages(
                { ChangeSignInStateReducer(SubmitButtonState.IDLE) },
                { ErrorEvent(it.message.toString()) }
            ),
            onComplete = messages { ChangeSignInStateReducer(SubmitButtonState.SUCCESS) }
        ).bindToView()

        val delaySuccessButtonStateObservable = stateObservable
            .getTrue { it.buttonState == SubmitButtonState.SUCCESS }
            .delay(2000L, TimeUnit.MILLISECONDS)
            .share()

        delaySuccessButtonStateObservable
            .map { ChangeButtonStateReducer(SubmitButtonState.DISABLED) }
            .bindToView()
    }

    private fun isChangedProfile(state: ProfileState): SubmitButtonState {
        return if (state.firstName != state.profile.firstName ||
            state.lastName != state.profile.lastName ||
            state.email != state.profile.email ||
            state.phone != state.profile.phone
        ) SubmitButtonState.IDLE else SubmitButtonState.DISABLED
    }
}
