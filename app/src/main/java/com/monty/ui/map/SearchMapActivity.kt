package com.monty.ui.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.monty.R
import com.monty.data.model.ui.Address
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.visible
import com.monty.ui.base.BaseActivity
import com.monty.ui.base.SubmitButtonState
import com.monty.ui.map.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search_map.*
import javax.inject.Inject

class SearchMapActivity : BaseActivity<SearchMapState>() {

    @Inject
    lateinit var reactorFactory: SearchMapReactorFactory

    private lateinit var googleMap: GoogleMap

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, SearchMapActivity::class.java)
    }

    override fun createReactor(): MviReactor<SearchMapState> {
        return getReactor(reactorFactory, SearchMapReactor::class.java)
    }

    override val layoutRes: Int = R.layout.activity_search_map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        search_map_button.buttonState = SubmitButtonState.IDLE

        search_map_toolbar.navigationClicks()
            .map { OnBackAction }
            .bindToReactor()

        search_map_button.onIdleButtonClickSubject
            .map {
                val position = googleMap.cameraPosition.target
                OnConfirmNewMarkerPositionAction(position.latitude, position.longitude)
            }.bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<SearchMapState>) {
        stateObservable.getChange { it.address }
            .observeState { address ->
                prepareGoogleMap(address)
            }
    }

    private fun prepareGoogleMap(address: Address) {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.search_map_map) as SupportMapFragment
        val callback = OnMapReadyCallback { googleMap ->
            this.googleMap = googleMap
            search_map_button.visible()

            googleMap.apply {
                val location = LatLng(address.latitude, address.longitude)
                val zoom = if(address == Address.PRAGUE) 10f else 16f
                val cameraPosition = CameraPosition.Builder().target(location).zoom(zoom).build()
                moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                uiSettings.isMapToolbarEnabled = false
            }
        }
        mapFragment.getMapAsync(callback)
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<SearchMapState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                BackEvent -> onBackPressed()
                is FinishWithResult -> finishWithResult(Intent().apply {
                    putExtra(Constant.Bundle.ADDRESS, event.address)
                })
            }
        }
    }

    private fun finishWithResult(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
