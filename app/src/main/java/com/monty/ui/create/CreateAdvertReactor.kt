package com.monty.ui.create

import com.monty.ui.create.contract.ChangeDepositReducer
import com.monty.ui.create.contract.ChangeDescriptionReducer
import com.monty.ui.create.contract.ChangePriceReducer
import com.monty.ui.create.contract.ChangeTitleReducer
import com.monty.ui.create.contract.CreateAdvertState
import com.monty.ui.create.contract.OnAddImageClickAction
import com.monty.ui.create.contract.OnDepositChangeAction
import com.monty.ui.create.contract.OnDescriptionChangeAction
import com.monty.ui.create.contract.OnImageChangeAction
import com.monty.ui.create.contract.OnPriceChangeAction
import com.monty.ui.create.contract.OnTitleChangeAction
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviAction
import io.reactivex.Observable
import javax.inject.Inject

class CreateAdvertReactor @Inject constructor() : MviReactor<CreateAdvertState>() {

    override fun createInitialState() = CreateAdvertState.INITIAL

    override fun bind(actions: Observable<MviAction<CreateAdvertState>>) {
        val onTitleChangeAction = actions.ofActionType<OnTitleChangeAction>()
        val onDescriptionChangeAction = actions.ofActionType<OnDescriptionChangeAction>()
        val onImageChangeAction = actions.ofActionType<OnImageChangeAction>()
        val onPriceChangeAction = actions.ofActionType<OnPriceChangeAction>()
        val onDepositChangeAction = actions.ofActionType<OnDepositChangeAction>()
        val onAddImageClickAction = actions.ofActionType<OnAddImageClickAction>()
        //val onAddAdvertAction = actions.ofActionType<OnAdve>()

        onTitleChangeAction.map { ChangeTitleReducer(it.title) }.bindToView()
        onDescriptionChangeAction.map { ChangeDescriptionReducer(it.description) }.bindToView()

        onPriceChangeAction
            .filter { it.price.isNotEmpty() }
            .map { ChangePriceReducer(it.price.toFloat()) }
            .bindToView()

        onDepositChangeAction
            .filter { it.deposit.isNotEmpty() }
            .map { ChangeDepositReducer(it.deposit.toFloat()) }
            .bindToView()

        //onAddAdvertAction
        //    .map { NavigateToCreateAdvertEvent }
        //    .bindToView()
    }
}
