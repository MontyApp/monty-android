package com.monty.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import com.monty.R
import com.monty.data.model.ui.Advert
import com.monty.tool.constant.Constant
import com.monty.tool.currency.CurrencyFormatter
import com.monty.ui.base.BaseActivity
import com.monty.ui.detail.contract.*
import com.monty.ui.photo.ShowPhotoActivity
import com.squareup.picasso.Picasso
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_advert_detail.*
import javax.inject.Inject

class AdvertDetailActivity : BaseActivity<AdvertDetailState>() {

    @Inject lateinit var reactorFactory: AdvertDetailReactorFactory

    @Inject lateinit var currencyFormatter: CurrencyFormatter

    companion object {
        fun getStartIntent(context: Context, advertId: Int): Intent {
            return Intent(context, AdvertDetailActivity::class.java).apply {
                putExtra(Constant.Bundle.ADVERT_ID, advertId)
            }
        }
    }

    override val layoutRes: Int = R.layout.activity_advert_detail

    override fun createReactor(): MviReactor<AdvertDetailState> {
        return getReactor(reactorFactory, AdvertDetailReactor::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        advert_detail_toolbar.navigationClicks()
            .map { OnBackAction }
            .bindToReactor()

        advert_detail_image.clicks()
            .map { OnPhotoClick }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<AdvertDetailState>) {
        stateObservable.getChange { it.advert }
            .filter { it != Advert.EMPTY }
            .observeState {
                advert_detail_title.text = it.title
                advert_detail_description.text = it.description
                advert_detail_price.text = it.getPrice(currencyFormatter)
                advert_detail_price_interval.text = it.getInterval(resources)
                advert_detail_deposit_price.text = it.getDeposit(currencyFormatter)
            }

        stateObservable.getChange { it.advert.image }
            .filter { it.isNotEmpty() }
            .observeState {
                Picasso.with(this)
                    .load(it)
                    .fit()
                    .centerCrop()
                    .into(advert_detail_image)
            }

        stateObservable.getChange { it.layoutState }
            .observeState { advert_detail_stateLayout.setState(it) }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<AdvertDetailState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                is NavigateToShowPhotoEvent -> {
                    startActivity(ShowPhotoActivity.getStartIntent(this, event.url))
                }
                BackEvent -> finish()
            }
        }
    }
}
