package br.com.mynotes.features.notes.domain.initializer

import android.content.Context
import androidx.startup.Initializer
import br.com.mynotes.commom.util.PreferencesWrapper

class PreferencesInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        PreferencesWrapper.initPreferences(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}