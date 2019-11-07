package com.monty.tool.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.ViewHolder.withAdapterPosition(block: (adapterPosition: Int) -> Unit) {
    val adapterPosition = this.adapterPosition
    if (adapterPosition != RecyclerView.NO_POSITION) {
        block.invoke(adapterPosition)
    }
}
