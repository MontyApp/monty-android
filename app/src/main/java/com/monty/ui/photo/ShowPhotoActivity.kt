package com.monty.ui.photo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.monty.R
import com.monty.data.model.ui.type.PhotoAddressType
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.gone
import com.monty.ui.base.BaseActivity
import com.monty.ui.photo.contract.BackEvent
import com.monty.ui.photo.contract.OnBackAction
import com.monty.ui.photo.contract.ShowPhotoState
import com.squareup.picasso.Picasso
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_show_photo.*
import java.io.File
import javax.inject.Inject

class ShowPhotoActivity : BaseActivity<ShowPhotoState>() {

    @Inject
    lateinit var reactorFactory: ShowPhotoReactorFactory

    companion object {
        fun getStartIntent(context: Context, address: String, addressType: PhotoAddressType = PhotoAddressType.URL): Intent {
            return Intent(context, ShowPhotoActivity::class.java).apply {
                putExtra(Constant.Bundle.PHOTO_ADDRESS_TYPE, addressType.value)
                putExtra(Constant.Bundle.PHOTO_ADDRESS, address)
            }
        }
    }

    override fun createReactor(): MviReactor<ShowPhotoState> {
        return getReactor(reactorFactory, ShowPhotoReactor::class.java)
    }

    override val layoutRes: Int = R.layout.activity_show_photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        show_photo_toolbar.navigationClicks()
            .map { OnBackAction }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<ShowPhotoState>) {
        stateObservable.getChange { it }
            .filter { it.photoAddress.isNotEmpty() }
            .observeState {
                if (it.photoAddressType == PhotoAddressType.URL.value) {
                    showPhoto(Uri.parse(it.photoAddress))
                } else {
                    showPhoto(Uri.fromFile(File(it.photoAddress)))
                }
            }
    }

    private fun showPhoto(uri: Uri) {
        Picasso.with(this)
            .load(uri)
            .into(show_photo_view, object : com.squareup.picasso.Callback {
                override fun onError() {}

                override fun onSuccess() {
                    show_photo_loading.gone()
                }
            })
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<ShowPhotoState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                BackEvent -> onBackPressed()
            }
        }
    }
}
