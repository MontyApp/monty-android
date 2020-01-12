package com.monty.ui.common.category.holder

import android.view.View
import com.monty.ui.common.category.CategoriesAdapter
import com.monty.ui.common.category.CategoryListItem
import com.monty.ui.common.category.TitleItem
import kotlinx.android.synthetic.main.item_title.view.*

class CategoryTitleViewHolder(
    itemView: View
) : CategoriesAdapter.ViewHolder(itemView) {

    override fun bindType(item: CategoryListItem) {
        (item as? TitleItem)?.let {
            itemView.bindView(it.name)
        }
    }

    private fun View.bindView(name: String) {
        title_name.text = name
    }
}
