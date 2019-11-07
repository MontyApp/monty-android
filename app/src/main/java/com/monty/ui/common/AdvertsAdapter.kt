package com.monty.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.monty.R
import com.monty.data.model.ui.Advert
import com.monty.tool.extensions.withAdapterPosition
import kotlinx.android.synthetic.main.card_advert.view.*
import javax.inject.Inject

class AdvertsAdapter @Inject constructor() : RecyclerView.Adapter<AdvertsAdapter.ViewHolder>() {

    val onItemClick: PublishRelay<Advert> = PublishRelay.create()

    private val items = ArrayList<Advert>()

    override fun getItemCount(): Int = items.size

    fun updateData(adverts: List<Advert>) {
        this.items.clear()
        this.items += adverts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_advert, parent, false)
        val viewHolder = ViewHolder(view)

        view.advert.setOnClickListener {
            viewHolder.withAdapterPosition { adapterPosition ->
                onItemClick.accept(items[adapterPosition])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            advert.init(items[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}
