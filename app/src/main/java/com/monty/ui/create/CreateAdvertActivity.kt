package com.monty.ui.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.monty.R
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.titleTypeface
import com.monty.ui.base.BaseActivity
import com.monty.ui.create.contract.CreateAdvertState
import com.monty.ui.create.contract.OnAddImageClickAction
import com.monty.ui.create.contract.OnDepositChangeAction
import com.monty.ui.create.contract.OnDescriptionChangeAction
import com.monty.ui.create.contract.OnPriceChangeAction
import com.monty.ui.create.contract.OnTitleChangeAction
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_create_advert.*
import javax.inject.Inject

class CreateAdvertActivity : BaseActivity<CreateAdvertState>() {

    @Inject lateinit var reactorFactory: CreateAdvertReactorFactory

    companion object {
        fun getStartIntent(context: Context, advertId: Int? = null): Intent {
            return Intent(context, CreateAdvertActivity::class.java).apply {
                putExtra(Constant.Bundle.ADVERT_ID, advertId)
            }
        }
    }

    override fun createReactor(): MviReactor<CreateAdvertState> {
        return getReactor(reactorFactory, CreateAdvertReactor::class.java)
    }

    override val layoutRes: Int = R.layout.activity_create_advert

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create_advert_toolbar_layout.titleTypeface()

        create_advert_titleEditText.textChanges()
            .map { OnTitleChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_descriptionEditText.textChanges()
            .map { OnDescriptionChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_priceEditText.textChanges()
            .map { OnPriceChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_depositEditText.textChanges()
            .map { OnDepositChangeAction(it.toString()) }
            .bindToReactor()

        create_advert_placeholder.clicks()
            .map { OnAddImageClickAction }
            .bindToReactor()

        //create
        //    .map { OnAddAdvertAction }
        //    .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<CreateAdvertState>) {
        //stateObservable.getChange { it.myAdverts }
        //    .observeState { adapter.updateData(it) }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<CreateAdvertState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {

            }
        }
    }
}
