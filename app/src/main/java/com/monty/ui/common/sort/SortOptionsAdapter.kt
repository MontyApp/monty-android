package com.monty.ui.common.sort

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.monty.R
import com.monty.tool.extensions.withAdapterPosition
import com.monty.ui.common.sort.holder.SortOptionViewHolder
import javax.inject.Inject

class SortOptionsAdapter @Inject constructor() :
    RecyclerView.Adapter<SortOptionsAdapter.ViewHolder>() {

    val onItemClick: PublishRelay<SortOption> = PublishRelay.create()

    private val items = ArrayList<SortOptionsListItem>()

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindType(item)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(selectedSortOption: SortOption) {
        this.items.clear()
        this.items += SortOptionItem(SortOption.NEWEST, selectedSortOption == SortOption.NEWEST)
        this.items += SortOptionItem(SortOption.NEAREST, selectedSortOption == SortOption.NEAREST)
        notifyDataSetChanged()
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindType(item: SortOptionsListItem)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        val layout = R.layout.item_sort_option
        val view: View = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
        return SortOptionViewHolder(view).apply {
            itemView.setOnClickListener {
                withAdapterPosition { adapterPosition ->
                    onItemClick.accept((items[adapterPosition] as? SortOptionItem)?.sortOption)
                }
            }
        }
    }
}
