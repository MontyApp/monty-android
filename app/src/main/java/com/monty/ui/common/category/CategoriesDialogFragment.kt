package com.monty.ui.common.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.monty.R
import com.monty.tool.extensions.halfExpandBottomSheet
import com.monty.ui.base.BaseBottomSheetFragment
import kotlinx.android.synthetic.main.view_categories_dialog.view.*

class CategoriesDialogFragment : BaseBottomSheetFragment() {

    private lateinit var adapter: CategoriesAdapter

    fun setAdapter(adapter: CategoriesAdapter) {
        this.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.halfExpandBottomSheet(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_categories_dialog, container, false).apply {
            categories_recycler.layoutManager = LinearLayoutManager(context)
            categories_recycler.adapter = adapter
        }
    }
}
