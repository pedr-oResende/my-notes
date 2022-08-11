package br.com.mynotes

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import br.com.mynotes.commom.util.PreferencesWrapper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyNotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesWrapper.initPreferences(this)
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        fun getContext() = context
    }
}