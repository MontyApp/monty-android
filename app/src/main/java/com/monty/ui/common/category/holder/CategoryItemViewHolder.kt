package com.monty.ui.common.category.holder

import android.view.View
import androidx.core.content.ContextCompat
import com.monty.R
import com.monty.data.model.ui.Category
import com.monty.tool.extensions.gone
import com.monty.tool.extensions.visible
import com.monty.ui.common.category.CategoriesAdapter
import com.monty.ui.common.category.CategoryItem
import com.monty.ui.common.category.CategoryListItem
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryItemViewHolder(
    itemView: View
) : CategoriesAdapter.ViewHolder(itemView) {

    override fun bindType(item: CategoryListItem) {
        (item as? CategoryItem)?.let {
            itemView.bindView(it.category, it.isSelected)
        }
    }

    private fun View.bindView(category: Category, isSelected: Boolean) {
        if (isSelected) {
            category_background.background = context.getDrawable(R.drawable.bg_selected_category)
            category_icon.setColorFilter(ContextCompat.getColor(context, R.color.red))
            category_name.setTextColor(ContextCompat.getColor(context, R.color.red))
            category_check.visible()
        } else {
            category_background.background = context.getDrawable(R.drawable.bg_selected)
            category_icon.setColorFilter(ContextCompat.getColor(context, R.color.blueish_grey))
            category_name.setTextColor(ContextCompat.getColor(context, R.color.blueish_grey))
            category_check.gone()
        }
        category_name.text = category.name
    }
}
