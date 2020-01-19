package com.monty.ui.common.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.monty.R
import com.monty.tool.extensions.expandBottomSheet
import com.monty.ui.base.BaseBottomSheetFragment
import kotlinx.android.synthetic.main.view_sort_dialog.view.*

class SortOptionDialogFragment : BaseBottomSheetFragment() {

    private lateinit var adapter: SortOptionsAdapter

    fun setAdapter(adapter: SortOptionsAdapter) {
        this.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.expandBottomSheet(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_sort_dialog, container, false).apply {
            sort_options_recycler.layoutManager = LinearLayoutManager(context)
            sort_options_recycler.adapter = adapter
        }
    }
}
