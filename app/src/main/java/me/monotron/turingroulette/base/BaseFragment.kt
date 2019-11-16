package me.monotron.turingroulette.base

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasSupportFragmentInjector {

    @Inject lateinit var fragmentDispatchingInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context) {

        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }
}