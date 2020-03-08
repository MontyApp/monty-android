package com.monty.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jakewharton.rxbinding2.widget.textChanges
import com.monty.R
import com.monty.data.model.ui.User
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.titleTypeface
import com.monty.tool.extensions.visible
import com.monty.tool.transform.CircleTransform
import com.monty.ui.base.BaseFragment
import com.monty.ui.profile.contract.*
import com.squareup.picasso.Picasso
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileState>() {

    @Inject lateinit var reactorFactory: ProfileReactorFactory

    @Inject lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun createReactor(): MviReactor<ProfileState> {
        return getReactor(reactorFactory, ProfileReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_collapsing_toolbar.titleTypeface()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        profile_first_nameEditText.textChanges()
            .map { OnFirstNameChangeAction(it.toString()) }
            .bindToReactor()

        profile_last_nameEditText.textChanges()
            .map { OnLastNameChangeAction(it.toString()) }
            .bindToReactor()

        profile_email_editText.textChanges()
            .map { OnEmailChangeAction(it.toString()) }
            .bindToReactor()

        profile_phone_editText.textChanges()
            .map { OnPhoneChangeAction(it.toString()) }
            .bindToReactor()

        profile_save_button.onIdleButtonClickSubject
            .map { OnButtonAction }
            .bindToReactor()

        profile_sign_in_button.onIdleButtonClickSubject
            .map { OnSignInAction }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<ProfileState>) {
        stateObservable.getChange { it.profile }
            .observeState { profile ->
                profile_sign_in.visible(profile == User.EMPTY)
                profile_content.visible(profile != User.EMPTY)
            }

        stateObservable.getChange { it.profile }
            .filter { it != User.EMPTY }
            .observeState { profile ->
                profile_first_nameEditText.setText(profile.firstName)
                profile_last_nameEditText.setText(profile.lastName)
                profile_email_editText.setText(profile.email)
                profile_phone_editText.setText(profile.phone)

                if (profile.avatar.isNotEmpty()) {
                    Picasso.with(context)
                        .load(profile.avatar)
                        .placeholder(R.drawable.bg_person)
                        .transform(CircleTransform())
                        .into(profile_photo)
                }
            }

        stateObservable.getChange { it.buttonState }
            .observeState { buttonState ->
                profile_save_button.buttonState = buttonState
            }

        stateObservable.getChange { it.signInState }
            .observeState { signInState ->
                profile_sign_in_button.buttonState = signInState
            }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<ProfileState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                SignInEvent -> {
                    val signInIntent = googleSignInClient.signInIntent
                    startActivityForResult(signInIntent, Constant.Intent.SIGN_IN_REQUEST)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.Intent.SIGN_IN_REQUEST) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                sendAction(OnSignInStartAction)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    sendAction(OnSignInSuccessAction(user))
                }
            }
    }
}
