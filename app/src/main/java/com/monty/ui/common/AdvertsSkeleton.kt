package com.monty.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.monty.R
import javax.inject.Inject

class AdvertsSkeleton @Inject constructor() {

    private var skeleton: RecyclerViewSkeletonScreen? = null

    private lateinit var recycler: RecyclerView

    private lateinit var adapter: AdvertsAdapter

    fun init(recycler: RecyclerView, adapter: AdvertsAdapter) {
        this.recycler = recycler
        this.adapter = adapter
    }

    fun show() {
        skeleton = Skeleton.bind(recycler)
            .adapter(adapter)
            .angle(0)
            .color(R.color.light_grey)
            .load(R.layout.view_skeleton_advert)
            .show()
    }

    fun hide() {
        skeleton?.hide()
    }
}