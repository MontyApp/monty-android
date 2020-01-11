package com.monty.data.model.ui

import androidx.room.ColumnInfo
import androidx.room.Entity

data class IntervalData(
    override val id: String,
    override val name: String
) : SpinnerData