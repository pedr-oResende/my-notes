package br.com.mynotes.commom.util

import android.content.Context
import android.content.SharedPreferences
import br.com.mynotes.features.notes.ui.screens.main.ui.DrawerScreens
import io.paperdb.Paper
import timber.log.Timber

class PreferencesWrapper private constructor(context: Context) {
    
    private var screen: DrawerScreens? = null
    
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
        return sharedPreferences?.getString(key, "")
    }

    fun getString(key: String?, valueDefault: String? = ""): String? {
        return sharedPreferences?.getString(key, valueDefault)
    }

    fun getBoolean(key: String?, valueDefault: Boolean = true): Boolean? {
        return sharedPreferences?.getBoolean(key, valueDefault)
    }

    fun getInt(key: String?, valueDefault: Int = 0): Int? {
        return sharedPreferences?.getInt(key, valueDefault)
    }

    fun getLong(key: String?): Long? {
        return sharedPreferences?.getLong(key, 0)
    }

    private fun save(key: String, value: String?) {
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)?.apply()
    }

    private fun save(key: String, value: Boolean) {
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)?.apply()
    }

    private fun save(key: String, value: Int) {
        val editor = sharedPreferences?.edit()
        editor?.putInt(key, value)?.apply()
    }

    private fun save(key: String, value: Long) {
        val editor = sharedPreferences?.edit()
        editor?.putLong(key, value)?.apply()
    }

    @Synchronized
    fun getScreen(): DrawerScreens? {
        if (screen == null) {
            try {
                screen = Paper.book(PAPER_USER_BOOK).read(PreferencesKey.SCREEN_STATE_KEY)
            } catch (e: Exception) {
                Timber.e(e, "Error getting screen from paperDB")
            }
        }
        return screen
    }

    @Synchronized
    fun setScreen(screen: DrawerScreens) {
        this.screen = screen
        try {
            val book = Paper.book(PAPER_USER_BOOK)
            book.delete(PreferencesKey.SCREEN_STATE_KEY)
            book.write(PreferencesKey.SCREEN_STATE_KEY, screen)
        } catch (e: Exception) {
            Timber.e(e, "Error setting screen")
        }
    }

    companion object {
        private const val PAPER_USER_BOOK = "USER_BOOK"
        
        private var preferencesWrapper: PreferencesWrapper? = null
        private var sharedPreferences: SharedPreferences? = null
        fun initPreferences(context: Context) {
            if (preferencesWrapper == null) {
                preferencesWrapper = PreferencesWrapper(context.applicationContext)
            }
        }

        val instance: PreferencesWrapper?
            get() {
                checkNotNull(preferencesWrapper) {
                    "Preferences Wrapper wasn't initialized. " +
                            "Call initPreferences(Context context) to initialize this."
                }
                return preferencesWrapper
            }
    }

    init {
        sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}