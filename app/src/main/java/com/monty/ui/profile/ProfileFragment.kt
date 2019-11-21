package com.monty.ui.profile

import android.os.Bundle
import android.view.View
import com.monty.R
import com.monty.tool.extensions.titleTypeface
import com.monty.ui.base.BaseFragment
import com.monty.ui.profile.contract.ProfileState
import com.sumera.koreactor.reactor.MviReactor
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileState>() {

    @Inject lateinit var reactorFactory: ProfileReactorFactory

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun createReactor(): MviReactor<ProfileState> {
        return getReactor(reactorFactory, ProfileReactor::class.java)
    }

    override val layoutRes: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_collapsing_toolbar.titleTypeface()
    }

    override fun bindToState(stateObservable: Observable<ProfileState>) {}
}
