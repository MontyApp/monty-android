package com.monty.ui.common.category.holder

import android.view.View
import com.monty.data.model.ui.Category
import com.monty.ui.common.category.CategoriesAdapter
import com.monty.ui.common.category.CategoryItem
import com.monty.ui.common.category.CategoryListItem
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryItemViewHolder(
    itemView: View
) : CategoriesAdapter.ViewHolder(itemView) {

    override fun bindType(item: CategoryListItem) {
        (item as? CategoryItem)?.let {
            itemView.bindView(it.category)
        }
    }

    private fun View.bindView(category: Category) {
        category_name.text = category.name
    }
}
