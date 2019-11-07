package com.monty.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.monty.R
import com.monty.data.model.ui.Advert
import com.monty.tool.extensions.drawable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_advert.view.*

class AdvertView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var advertView: View = View.inflate(getContext(), R.layout.view_advert, this)

    private lateinit var advert: Advert

    fun init(advert: Advert) {
        this.advert = advert
        advertView.initAdvert()
    }

    private fun View.initAdvert() {
        advert_title.text = advert.title
        advert_price.text = advert.price
        advert_location.text = "Brno"
        advert_distance.text = "800 m"

        Picasso.with(context)
            .load(advert.image)
            // TODO add placeholder
            //.placeholder(R.drawable.ic_search)
            .fit()
            .centerCrop()
            .into(advert_image)

        advert_favourite.setImageDrawable(
            if (advert.isFavourite) {
                context.drawable(R.drawable.ic_favourite_active_white)
            } else {
                context.drawable(R.drawable.ic_favourite_inactive)
            }
        )
    }
}
