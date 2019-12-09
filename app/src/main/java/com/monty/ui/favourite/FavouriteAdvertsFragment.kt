package com.monty.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.monty.R
import com.monty.tool.extensions.titleTypeface
import com.monty.ui.base.BaseFragment
import com.monty.ui.common.AdvertsAdapter
import com.monty.ui.detail.AdvertDetailActivity
import com.monty.ui.favourite.contract.FavouriteAdvertsState
import com.monty.ui.favourite.contract.NavigateToAdvertDetailEvent
import com.monty.ui.favourite.contract.OnAdvertClickAction
import com.monty.ui.favourite.contract.OnFavouriteClickAction
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_favourite_adverts.*
import javax.inject.Inject

class FavouriteAdvertsFragment : BaseFragment<FavouriteAdvertsState>() {

    @Inject lateinit var reactorFactory: FavouriteAdvertsReactorFactory
    @Inject lateinit var adapter: AdvertsAdapter

    companion object {
        fun newInstance() = FavouriteAdvertsFragment()
    }

    override fun createReactor(): MviReactor<FavouriteAdvertsState> {
        return getReactor(reactorFactory, FavouriteAdvertsReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_favourite_adverts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favourite_adverts_toolbar_layout.titleTypeface()

        favourite_adverts_recycler.layoutManager = LinearLayoutManager(context)
        favourite_adverts_recycler.adapter = adapter

        adapter.onItemClick
            .map { OnAdvertClickAction(it) }
            .bindToReactor()

        adapter.onFavouriteClick
            .map { OnFavouriteClickAction(it) }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<FavouriteAdvertsState>) {
        stateObservable.getChange { it.favouriteAdverts }
            .observeState { adapter.updateData(it) }

        stateObservable.getChange { it.layoutState }
            .observeState { favourite_adverts_stateLayout.setState(it) }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<FavouriteAdvertsState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                is NavigateToAdvertDetailEvent -> {
                    startActivity(AdvertDetailActivity.getStartIntent(requireContext(), event.advertId))
                }
            }
        }
    }
}
