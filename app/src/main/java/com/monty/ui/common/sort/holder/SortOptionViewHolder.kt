package com.monty.ui.common.sort.holder

import android.view.View
import androidx.core.content.ContextCompat
import com.monty.R
import com.monty.tool.extensions.gone
import com.monty.tool.extensions.visible
import com.monty.ui.common.sort.SortOption
import com.monty.ui.common.sort.SortOptionItem
import com.monty.ui.common.sort.SortOptionsAdapter
import com.monty.ui.common.sort.SortOptionsListItem
import kotlinx.android.synthetic.main.item_sort_option.view.*

class SortOptionViewHolder(
    itemView: View
) : SortOptionsAdapter.ViewHolder(itemView) {

    override fun bindType(item: SortOptionsListItem) {
        (item as? SortOptionItem)?.let {
            itemView.bindView(it.sortOption, it.isSelected)
        }
    }

    private fun View.bindView(sortOption: SortOption, isSelected: Boolean) {
        if (isSelected) {
            sort_option_background.background = context.getDrawable(R.drawable.bg_selected_category)
            sort_option_name.setTextColor(ContextCompat.getColor(context, R.color.red))
            sort_option_check.visible()
        } else {
            sort_option_background.background = context.getDrawable(R.drawable.bg_selected)
            sort_option_name.setTextColor(ContextCompat.getColor(context, R.color.blueish_grey))
            sort_option_check.gone()
        }
        when (sortOption) {
            SortOption.NEAREST -> sort_option_name.setText(R.string.sort_advert_by_nearest)
            SortOption.NEWEST -> sort_option_name.setText(R.string.sort_advert_by_newest)
        }
    }
}
