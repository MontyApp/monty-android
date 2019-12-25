package com.monty.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.monty.R
import com.monty.data.model.ui.User
import com.monty.tool.transform.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_user.view.*

class UserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var userView: View = View.inflate(getContext(), R.layout.view_user, this)

    private lateinit var user: User

    fun init(user: User) {
        this.user = user
        userView.initAdvert()
    }

    private fun View.initAdvert() {
        user_name.text = user.name
        user_adverts_count.text = "5 inzerátov"

        Picasso.with(context)
            .load(user.photo)
            .transform(CircleTransform())
            .into(user_photo)
    }
}
