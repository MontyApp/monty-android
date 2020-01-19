package com.monty.ui.common.sort

sealed class SortOptionsListItem {
    abstract val type: Int

    companion object {
        const val TYPE_SORT_OPTION = 1
    }
}

data class SortOptionItem(
    var sortOption: SortOption,
    var isSelected: Boolean,
    override val type: Int = TYPE_SORT_OPTION
) : SortOptionsListItem()
