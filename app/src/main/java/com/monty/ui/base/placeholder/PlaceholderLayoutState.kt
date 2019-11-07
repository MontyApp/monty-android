package com.monty.ui.base.placeholder

enum class ViewState {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR,
    UNSPECIFIED
}

enum class PullState {
    IDLE,
    REFRESHING,
    UNSPECIFIED,
    DISABLED
}

data class PlaceholderLayoutState(
    val viewState: ViewState,
    val pullState: PullState
) {
    companion object {
        val DEFAULT = PlaceholderLayoutState(
            ViewState.UNSPECIFIED,
            PullState.UNSPECIFIED
        )
    }
}

data class PartialLayoutState(
    val viewState: ViewState = ViewState.UNSPECIFIED,
    val pullState: PullState = PullState.UNSPECIFIED
) {
    fun toFull(oldState: PlaceholderLayoutState): PlaceholderLayoutState {
        return PlaceholderLayoutState(
            viewState = if (this.viewState != ViewState.UNSPECIFIED) this.viewState else oldState.viewState,
            pullState = if (this.pullState != PullState.UNSPECIFIED) this.pullState else oldState.pullState
        )
    }
}
