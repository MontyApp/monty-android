package com.monty.ui.adverts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.monty.R
import com.monty.ui.adverts.contract.AdvertsState
import com.monty.ui.adverts.contract.OnAdvertClickAction
import com.monty.ui.base.BaseFragment
import com.monty.ui.common.AdvertsAdapter
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_adverts.*
import javax.inject.Inject

class AdvertsFragment : BaseFragment<AdvertsState>() {

    @Inject lateinit var reactorFactory: AdvertsReactorFactory
    @Inject lateinit var adapter: AdvertsAdapter

    companion object {
        fun newInstance() = AdvertsFragment()
    }

    override fun createReactor(): MviReactor<AdvertsState> {
        return getReactor(reactorFactory, AdvertsReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_adverts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adverts_recycler.layoutManager = LinearLayoutManager(context)
        adverts_recycler.adapter = adapter

        adapter.onItemClick
            .map { OnAdvertClickAction(it) }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<AdvertsState>) {
        stateObservable.getChange { it.adverts }
            .observeState { adapter.updateData(it) }

        stateObservable.getChange { it.layoutState }
            .observeState { adverts_stateLayout.setState(it) }
    }
}
