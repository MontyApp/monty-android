package com.monty.ui.adverts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.monty.R
import com.monty.data.model.ui.Category
import com.monty.tool.extensions.gone
import com.monty.tool.extensions.titleTypeface
import com.monty.tool.extensions.visible
import com.monty.ui.adverts.contract.*
import com.monty.ui.base.BaseBottomSheetFragment
import com.monty.ui.base.BaseFragment
import com.monty.ui.common.AdvertsAdapter
import com.monty.ui.common.category.CategoriesAdapter
import com.monty.ui.common.category.CategoriesDialogFragment
import com.monty.ui.create.CreateAdvertActivity
import com.monty.ui.detail.AdvertDetailActivity
import com.sumera.koreactor.reactor.MviReactor
import com.sumera.koreactor.reactor.data.MviEvent
import com.sumera.koreactor.util.extension.getChange
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_adverts.*
import kotlinx.android.synthetic.main.view_categories_button.category_button
import kotlinx.android.synthetic.main.view_selected_category.*
import javax.inject.Inject

class AdvertsFragment : BaseFragment<AdvertsState>() {

    @Inject lateinit var reactorFactory: AdvertsReactorFactory
    @Inject lateinit var advertsAdapter: AdvertsAdapter
    @Inject lateinit var categoriesAdapter: CategoriesAdapter
    private var categoriesDialog: CategoriesDialogFragment? = null

    companion object {
        fun newInstance() = AdvertsFragment()
    }

    override fun createReactor(): MviReactor<AdvertsState> {
        return getReactor(reactorFactory, AdvertsReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_adverts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adverts_toolbar_layout.titleTypeface()

        adverts_recycler.layoutManager = LinearLayoutManager(context)
        adverts_recycler.adapter = advertsAdapter

        if (savedInstanceState != null) {
            categoriesDialog =
                requireFragmentManager().findFragmentByTag(BaseBottomSheetFragment.TAG) as? CategoriesDialogFragment
            bindDialogToReactor()
        }

        advertsAdapter.onItemClick
            .map { OnAdvertClickAction(it) }
            .bindToReactor()

        advertsAdapter.onFavouriteClick
            .map { OnFavouriteClickAction(it) }
            .bindToReactor()

        categoriesAdapter.onItemClick
            .map {
                categoriesDialog?.dismiss()
                OnCategoryClickAction(it)
            }.bindToReactor()

        category_button.clicks()
            .map { OnCategoriesClickAction }
            .bindToReactor()

        selected_category_close.clicks()
            .map { OnCategoryClickAction(Category.EMPTY) }
            .bindToReactor()

        adverts_toolbar_plus.clicks()
            .map { OnAddAdvertClickAction }
            .bindToReactor()
    }

    override fun bindToState(stateObservable: Observable<AdvertsState>) {
        stateObservable.getChange { Pair(it.adverts, it.selectedCategory) }
            .map { (adverts, selectedCategory) ->
                if (selectedCategory != Category.EMPTY) {
                    adverts.filter { it.categoryId == selectedCategory.id }
                } else {
                    adverts
                }
            }
            .observeState { advertsAdapter.updateData(it) }

        stateObservable.getChange { it.layoutState }
            .observeState { adverts_stateLayout.setState(it) }

        stateObservable.getChange { Pair(it.categories, it.selectedCategory) }
            .observeState { (categories, selected) ->
                categoriesAdapter.updateData(categories, selected)
            }

        stateObservable.getChange { it.selectedCategory }
            .observeState {
                if (it != Category.EMPTY) {
                    adverts_selected_category.visible()
                    selected_category_name.text = it.name
                } else {
                    adverts_selected_category.gone()
                }
            }
    }

    override fun bindToEvent(eventsObservable: Observable<MviEvent<AdvertsState>>) {
        eventsObservable.observeEvent { event ->
            when (event) {
                is NavigateToAdvertDetailEvent -> {
                    startActivity(
                        AdvertDetailActivity.getStartIntent(
                            requireContext(),
                            event.advertId
                        )
                    )
                }
                is AdvertsEvent -> showCategoriesDialog()
                is NavigateToCreateAdvertEvent -> {
                    startActivity(CreateAdvertActivity.getStartIntent(requireContext()))
                }
            }
        }
    }

    private fun bindDialogToReactor() {
        categoriesDialog?.setAdapter(categoriesAdapter)
    }

    private fun showCategoriesDialog() {
        categoriesDialog = CategoriesDialogFragment()
        categoriesDialog?.show(requireFragmentManager())
        bindDialogToReactor()
    }
}
