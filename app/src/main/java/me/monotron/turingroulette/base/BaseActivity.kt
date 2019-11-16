package me.monotron.turingroulette.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import me.monotron.turingroulette.application.TuringRouletteApplication
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var fragmentDispatchingInjector: DispatchingAndroidInjector<Fragment>

    lateinit var dependencies: TuringRouletteApplication

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        dependencies = application as TuringRouletteApplication
        dependencies.dependencyGraph.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {

        return fragmentDispatchingInjector
    }
}