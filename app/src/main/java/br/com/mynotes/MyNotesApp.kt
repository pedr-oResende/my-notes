package br.com.mynotes

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import br.com.mynotes.data.di.mapperModules
import br.com.mynotes.data.di.repositoryModules
import br.com.mynotes.domain.di.useCaseModules
import br.com.mynotes.presentation.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class MyNotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinModules()
        context = this
    }

    private fun initKoinModules() {
        startKoin {
            androidContext(this@MyNotesApp)
            androidFileProperties()
            modules(
                listOf(
                    viewModelModules,
                    repositoryModules,
                    useCaseModules,
                    mapperModules
                )
            )
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        fun getContext() = context
    }
}