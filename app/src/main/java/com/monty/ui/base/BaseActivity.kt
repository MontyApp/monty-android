package com.monty.ui.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.monty.R
import com.monty.tool.extensions.color
import com.monty.tool.extensions.withOpacity
import com.sumera.koreactor.reactor.data.MviState
import com.sumera.koreactor.view.implementation.MviActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<STATE> : MviActivity<STATE>(), HasSupportFragmentInjector
        where STATE : MviState {

    enum class StatusBar {
        LIGHT,
        DARK
    }

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    protected abstract val layoutRes: Int

    protected open val statusBar: StatusBar = StatusBar.DARK

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        if (layoutRes != 0) {
            setContentView(layoutRes)
        }

        setupStatusBar()
    }

    private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= 23) {
            var flags = if (statusBar == StatusBar.DARK) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window?.decorView?.systemUiVisibility = flags
            window?.statusBarColor = Color.TRANSPARENT
        } else {
            window?.statusBarColor = color(R.color.dark_grey).withOpacity(50)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    @Suppress("RedundantOverride")
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
