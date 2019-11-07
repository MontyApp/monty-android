package com.monty.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import com.monty.R
import com.monty.tool.constant.Constant
import com.monty.tool.extensions.getMenuItemPosition
import com.monty.ui.base.BaseActivity
import com.monty.ui.main.contract.MainState
import com.sumera.koreactor.reactor.MviReactor
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainState>() {

    @Inject lateinit var reactorFactory: MainReactorFactory
    @Inject lateinit var pagerAdapter: MainPagerAdapter

    private var selectedTabId: Int = R.id.empty

    companion object {
        fun getStartIntent(context: Context, @IdRes tabId: Int = R.id.empty): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(Constant.Bundle.TAB_ID, tabId)
            }
        }
    }

    override fun createReactor(): MviReactor<MainState> {
        return getReactor(reactorFactory, MainReactor::class.java)
    }

    override val layoutRes: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_viewPager.offscreenPageLimit = 4

        if (intent.hasExtra(Constant.Bundle.TAB_ID) && savedInstanceState == null) {
            selectedTabId = intent.getIntExtra(Constant.Bundle.TAB_ID, R.id.empty)
        } else if (savedInstanceState != null) {
            selectedTabId = savedInstanceState.getInt(Constant.Bundle.TAB_ID, R.id.empty)
        }

        main_bottomMenu.menu.clear()
        main_bottomMenu.inflateMenu(R.menu.main_menu)
        main_viewPager.adapter = pagerAdapter
        selectCorrectMenuTab()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            selectedTabId = it.getIntExtra(Constant.Bundle.TAB_ID, R.id.empty)
            selectCorrectMenuTab()
        }
        setIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constant.Bundle.TAB_ID, selectedTabId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            selectedTabId = it.getInt(Constant.Bundle.TAB_ID, R.id.empty)
            selectCorrectMenuTab()
        }
    }

    private fun selectCorrectMenuTab() {
        val menuItem: MenuItem? = main_bottomMenu.menu.findItem(selectedTabId)
        menuItem?.let {
            val itemPosition = main_bottomMenu.getMenuItemPosition(it)
            if (itemPosition != -1) {
                main_viewPager.setCurrentItem(itemPosition, false)
                main_bottomMenu.menu.getItem(itemPosition).isChecked = true
                main_bottomMenu.findViewById<View>(selectedTabId).performClick()
            }
        }
    }
}
