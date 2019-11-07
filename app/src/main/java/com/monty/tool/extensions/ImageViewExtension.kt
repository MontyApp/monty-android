package com.monty.tool.extensions

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.monty.tool.transform.CircleTransform
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String) {
    Picasso.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadImage(url: String, placeholderId: Int) {
    Picasso.with(context)
        .load(url)
        .placeholder(AppCompatResources.getDrawable(context, placeholderId))
        .into(this)
}

fun ImageView.loadCircleImage(url: String) {
    Picasso.with(context)
        .load(url)
        .transform(CircleTransform())
        .into(this)
}

fun ImageView.loadCircleImage(url: String, placeholderId: Int) {
    Picasso.with(context)
        .load(url)
        .placeholder(AppCompatResources.getDrawable(context, placeholderId))
        .transform(CircleTransform())
        .into(this)
}
