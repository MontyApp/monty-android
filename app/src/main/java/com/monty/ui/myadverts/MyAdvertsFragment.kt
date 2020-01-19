package com.monty.ui.myadverts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.monty.R
import com.monty.tool.extensions.titleTypeface
import com.monty.ui.base.BaseFragment
import com.monty.ui.common.AdvertsAdapter
import com.monty.ui.common.AdvertsSkeleton
import com.monty.ui.create.CreateAdvertActivity
import com.monty.ui.detail.AdvertDetailActivity
import com.monty.ui.myadverts.contract.*
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_my_adverts.*
import javax.inject.Inject

class MyAdvertsFragment : BaseFragment<MyAdvertsState>() {

    @Inject lateinit var reactorFactory: MyAdvertsReactorFactory
    @Inject lateinit var adapter: AdvertsAdapter
    @Inject lateinit var advertsSkeleton: AdvertsSkeleton

    companion object {
        fun newInstance() = MyAdvertsFragment()
    }

    override fun createReactor(): MviReactor<MyAdvertsState> {
        return getReactor(reactorFactory, MyAdvertsReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_my_adverts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        my_adverts_toolbar_layout.titleTypeface()

        my_adverts_recycler.layoutManager = LinearLayoutManager(context)
        my_adverts_recycler.adapter = adapter

        advertsSkeleton.init(my_adverts_recycler, adapter)
        advertsSkeleton.show()

        adapter.onItemClick
            .map { OnAdvertClickAction(it) }
            .bindToReactor()

        my_adverts_add.clicks()
            .map { OnAddAdvertAction }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<MyAdvertsState>) {
        stateObservable.getChange { it.myAdverts }
            .filter { it.isNotEmpty() }
            .observeState {
                advertsSkeleton.hide()
                adapter.updateData(it)
            }

        stateObservable.getChange { it.myLocation }
            .observeState { adapter.updateLocation(it) }

        stateObservable.getChange { it.layoutState }
            .observeState { my_adverts_stateLayout.setState(it) }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<MyAdvertsState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                is NavigateToAdvertDetailEvent -> {
                    startActivity(AdvertDetailActivity.getStartIntent(requireContext(), event.advertId))
                }
                is NavigateToCreateAdvertEvent -> {
                    startActivity(CreateAdvertActivity.getStartIntent(requireContext()))
                }
            }
        }
    }
}
