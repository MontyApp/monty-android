package com.monty.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import com.monty.R
import com.monty.data.model.ui.Address
import com.monty.data.model.ui.Advert
import com.monty.data.model.ui.User
import com.monty.tool.constant.Constant
import com.monty.tool.currency.CurrencyFormatter
import com.monty.tool.extensions.configureMap
import com.monty.tool.extensions.drawable
import com.monty.tool.intent.Navigation
import com.monty.ui.base.BaseActivity
import com.monty.ui.base.BaseBottomSheetFragment
import com.monty.ui.common.dialog.ContactDialog
import com.monty.ui.common.dialog.DeleteAdvertDialog
import com.monty.ui.create.CreateAdvertActivity
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

    private var deleteAdvertDialog: DeleteAdvertDialog? = null
    private var contactDialog: ContactDialog? = null

    companion object {
        fun getStartIntent(context: Context, advertId: String): Intent {
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

        if (savedInstanceState != null) {
            contactDialog =
                supportFragmentManager.findFragmentByTag(BaseBottomSheetFragment.TAG) as? ContactDialog
            deleteAdvertDialog =
                supportFragmentManager.findFragmentByTag(DeleteAdvertDialog.TAG) as? DeleteAdvertDialog
            bindDialogToReactor()
        }

        advert_detail_stateLayout.onRefresh.map { OnRefreshAction }.bindToReactor()

        advert_detail_toolbar.navigationClicks()
            .map { OnBackAction }
            .bindToReactor()

        advert_detail_image.clicks()
            .map { OnPhotoClick }
            .bindToReactor()

        advert_detail_contact.onIdleButtonClickSubject
            .map { OnContactAction }
            .bindToReactor()

        advert_detail_map_click.clicks()
            .map { OnMapAction }
            .bindToReactor()

        advert_detail_toolbar_favourite.clicks()
            .map { OnFavouriteAction }
            .bindToReactor()

        advert_detail_toolbar_edit.clicks()
            .map { OnEditAction }
            .bindToReactor()

        advert_detail_stateLayout.onRefresh
            .map { OnRefreshAction }
            .bindToReactor()

        advert_detail_toolbar_delete.clicks()
            .map { OnDeleteAction }
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

                if(it.isFavourite) {
                    advert_detail_toolbar_favourite.setImageDrawable(drawable(R.drawable.bg_favourite_active))
                } else {
                    advert_detail_toolbar_favourite.setImageDrawable(drawable(R.drawable.bg_favourite_inactive))
                }
            }

        stateObservable.getChange { it.user }
            .filter { it != User.EMPTY }
            .observeState { user ->
                advert_detail_user.init(user)
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

        stateObservable.getChange { Pair(it.advert.address, it.myLocation) }
            .filter { it.first != Address.EMPTY }
            .observeState { (address, myLocation) ->
                val addressLatLon = LatLng(address.latitude, address.longitude)
                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.advert_detail_map) as SupportMapFragment
                val callback = OnMapReadyCallback { googleMap ->
                    googleMap.configureMap(this, addressLatLon, myLocation)
                }
                mapFragment.getMapAsync(callback)
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
                is ShowContactDialog -> showContactDialog(event.name)
                is NavigateToPhoneEvent -> startActivity(Navigation.callPhone(event.phone))
                is NavigateToEmailEvent -> startActivity(Navigation.sendEmail(event.email, event.title))
                is NavigateToMapEvent -> startActivity(Navigation.showOnMap(event.lat, event.lon, this))
                is NavigateToEditEvent -> startActivity(CreateAdvertActivity.getStartIntent(this, advertId = event.advertId))
                is ShowDeleteDialogEvent -> showDeleteAdvertDialog()
                BackEvent -> finish()
            }
        }
    }

    private fun bindDialogToReactor() {
        contactDialog?.onPhoneClick
            ?.map { OnContactPhoneAction }
            ?.bindToReactor()

        contactDialog?.onEmailClick
            ?.map { OnContactEmailAction }
            ?.bindToReactor()

        deleteAdvertDialog?.onPositiveClick
            ?.map { OnDeletePositiveAction }
            ?.bindToReactor()
    }

    private fun showDeleteAdvertDialog() {
        deleteAdvertDialog = DeleteAdvertDialog()
        deleteAdvertDialog?.show(supportFragmentManager)
        bindDialogToReactor()
    }

    private fun showContactDialog(name: String) {
        contactDialog = ContactDialog.newInstance()
        contactDialog?.init(name)?.show(supportFragmentManager)
        bindDialogToReactor()
    }
}
