package br.com.mynotes.commom.util

import android.content.Context
import android.content.SharedPreferences
import br.com.mynotes.features.notes.presentation.screens.main.ScreenState

/**
 * Created by Fernando on 01/10/2016.
 */
class PreferencesWrapper private constructor(context: Context) {
    fun putString(key: String, value: String?) {
        save(key, value)
    }

    fun putInt(key: String, value: Int) {
        save(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        save(key, value)
    }

    fun putLong(key: String, value: Long) {
        save(key, value)
    }

    fun getString(key: String?): String? {
        return sSharedPreferences?.getString(key, "")
    }

    fun getString(key: String?, valueDefault: String? = ""): String? {
        return sSharedPreferences?.getString(key, valueDefault)
    }

    fun getBoolean(key: String?, valueDefault: Boolean = true): Boolean? {
        return sSharedPreferences?.getBoolean(key, valueDefault)
    }

    fun getInt(key: String?, valueDefault: Int = 0): Int? {
        return sSharedPreferences?.getInt(key, valueDefault)
    }

    fun getLong(key: String?): Long? {
        return sSharedPreferences?.getLong(key, 0)
    }

    private fun save(key: String, value: String?) {
        val editor = sSharedPreferences?.edit()
        editor?.putString(key, value)?.apply()
    }

    private fun save(key: String, value: Boolean) {
        val editor = sSharedPreferences?.edit()
        editor?.putBoolean(key, value)?.apply()
    }

    private fun save(key: String, value: Int) {
        val editor = sSharedPreferences?.edit()
        editor?.putInt(key, value)?.apply()
    }

    private fun save(key: String, value: Long) {
        val editor = sSharedPreferences?.edit()
        editor?.putLong(key, value)?.apply()
    }

    fun clearPreferences() {
        putString(key = PreferencesKey.SCREEN_STATE_KEY, value = ScreenState.HomeScreen.value)
    }

    companion object {
        private var sPreferencesWrapper: PreferencesWrapper? = null
        private var sSharedPreferences: SharedPreferences? = null
        fun initPreferences(context: Context) {
            if (sPreferencesWrapper == null) {
                sPreferencesWrapper = PreferencesWrapper(context.applicationContext)
            }
        }

        val instance: PreferencesWrapper?
            get() {
                checkNotNull(sPreferencesWrapper) {
                    "Preferences Wrapper wasn't initialized. " +
                            "Call initPreferences(Context context) to initialize this."
                }
                return sPreferencesWrapper
            }
    }

    init {
        sSharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}