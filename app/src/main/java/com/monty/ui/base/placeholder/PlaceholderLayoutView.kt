package com.monty.ui.base.placeholder

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.forEach
import com.monty.R
import com.monty.tool.extensions.visible
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_placeholder.view.*

class PlaceholderLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isInitialized = false

    private var loadingView: View? = null
    private var contentView: View? = null
    private var emptyView: View? = null
    private var errorView: View? = null

    val onRefresh: Observable<Unit> =
        Observable.create {
            placeholder_layout_swipeToRefresh.setOnRefreshListener {
                if (it.isDisposed.not()) {
                    it.onNext(Unit)
                }
            }
        }

    init {
        View.inflate(getContext(), R.layout.layout_placeholder, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!isInitialized) {
            initialize()
            isInitialized = true
        }
    }

    private fun initialize() {
        val childCount = childCount
        if (childCount == 0) {
            throw IllegalStateException("PlaceholderLayoutView must contain at least one child")
        }

        forEach { child ->
            when (child.id) {
                R.id.state_loading -> loadingView = child
                R.id.state_content -> contentView = child
                R.id.state_empty -> emptyView = child
                R.id.state_error -> errorView = child
            }
        }

        if (contentView == null) {
            throw IllegalStateException("PlaceholderLayoutView does not contain child with id R.id.state_content")
        }

        removeView(contentView)
        placeholder_layout_swipeToRefresh.addView(contentView)
    }

    fun setState(partialLayoutState: PartialLayoutState) {
        setState(PlaceholderLayoutState(partialLayoutState.viewState, partialLayoutState.pullState))
    }

    fun setState(placeholderLayoutState: PlaceholderLayoutState) {
        val shownLayoutId = when (placeholderLayoutState.viewState) {
            ViewState.LOADING -> {
                viewNotNull(loadingView, "PlaceholderLayoutView does not contain child with id R.id.state_loading")
                R.id.state_loading
            }
            ViewState.CONTENT -> {
                viewNotNull(contentView, "PlaceholderLayoutView does not contain child with id R.id.state_content")
                R.id.state_content
            }
            ViewState.EMPTY -> {
                viewNotNull(emptyView, "PlaceholderLayoutView does not contain child with id R.id.state_empty")
                R.id.state_empty
            }
            ViewState.ERROR -> {
                viewNotNull(errorView, "PlaceholderLayoutView does not contain child with id R.id.state_error")
                R.id.state_error
            }
            else -> R.id.state_unspecified
        }

        if (shownLayoutId != R.id.state_unspecified) {
            val showLoading = loadingView?.id == shownLayoutId

            loadingView?.visible(showLoading)
            contentView?.visible(contentView?.id == shownLayoutId)
            emptyView?.visible(emptyView?.id == shownLayoutId)
            errorView?.visible(errorView?.id == shownLayoutId)

            placeholder_layout_swipeToRefresh.isEnabled = !showLoading
        }

        when (placeholderLayoutState.pullState) {
            PullState.IDLE -> placeholder_layout_swipeToRefresh.isRefreshing = false
            PullState.REFRESHING -> placeholder_layout_swipeToRefresh.isRefreshing = true
            PullState.DISABLED -> placeholder_layout_swipeToRefresh.isEnabled = false
            else -> {
                // nothing to do
            }
        }
    }

    private fun viewNotNull(view: View?, exceptionMessage: String) {
        if (view == null) {
            throw IllegalStateException(exceptionMessage)
        }
    }
}
