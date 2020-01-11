package com.monty.ui.common.category

import com.monty.data.model.ui.Category

sealed class CategoryListItem {
    abstract val type: Int

    companion object {
        const val TYPE_CATEGORY = 1
        const val TYPE_TITLE = 2
    }
}

data class CategoryItem(
    var isSelected: Boolean,
    var category: Category,
    override val type: Int = TYPE_CATEGORY
) : CategoryListItem()

data class TitleItem(
    var name: String,
    override val type: Int = TYPE_TITLE
) : CategoryListItem()

