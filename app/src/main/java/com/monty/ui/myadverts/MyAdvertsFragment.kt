package com.monty.ui.myadverts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.monty.R
import com.monty.ui.base.BaseFragment
import com.monty.ui.common.AdvertsAdapter
import com.monty.ui.myadverts.contract.MyAdvertsState
import com.monty.ui.myadverts.contract.OnAdvertClickAction
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_my_adverts.*
import javax.inject.Inject

class MyAdvertsFragment : BaseFragment<MyAdvertsState>() {

    @Inject lateinit var reactorFactory: MyAdvertsReactorFactory
    @Inject lateinit var adapter: AdvertsAdapter

    companion object {
        fun newInstance() = MyAdvertsFragment()
    }

    override fun createReactor(): MviReactor<MyAdvertsState> {
        return getReactor(reactorFactory, MyAdvertsReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_my_adverts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_adverts_recycler.layoutManager = LinearLayoutManager(context)
        my_adverts_recycler.adapter = adapter

        adapter.onItemClick
            .map { OnAdvertClickAction(it) }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<MyAdvertsState>) {
        stateObservable.getChange { it.myAdverts }
            .observeState { adapter.updateData(it) }

        stateObservable.getChange { it.layoutState }
            .observeState { my_adverts_stateLayout.setState(it) }
    }
}
