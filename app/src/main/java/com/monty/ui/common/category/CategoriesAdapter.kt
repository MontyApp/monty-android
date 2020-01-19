package com.monty.ui.common.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.monty.R
import com.monty.data.model.ui.Category
import com.monty.tool.extensions.withAdapterPosition
import com.monty.ui.common.category.holder.CategoryItemViewHolder
import com.monty.ui.common.category.holder.CategoryTitleViewHolder
import javax.inject.Inject

class CategoriesAdapter @Inject constructor() :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    val onItemClick: PublishRelay<Category> = PublishRelay.create()

    private val items = ArrayList<CategoryListItem>()

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindType(item)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(categories: List<Category>, selectedCategory: Category) {
        this.items.clear()
        categories
            .groupBy { it.parent.id }
            .map {
                this.items.add(TitleItem(it.value.first().parent.name))
                this.items.addAll(it.value.map { category ->
                    CategoryItem(
                        isSelected = category == selectedCategory,
                        category = category
                    )
                })
            }
        notifyDataSetChanged()
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindType(item: CategoryListItem)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        val view: View
        return when (type) {
            CategoryListItem.TYPE_CATEGORY -> {
                val layout = R.layout.item_category
                view = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
                CategoryItemViewHolder(view).apply {
                    itemView.setOnClickListener {
                        withAdapterPosition { adapterPosition ->
                            onItemClick.accept((items[adapterPosition] as? CategoryItem)?.category)
                        }
                    }
                }
            }
            else -> {
                val layout = R.layout.item_title
                view = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
                CategoryTitleViewHolder(view)
            }
        }
    }
}
