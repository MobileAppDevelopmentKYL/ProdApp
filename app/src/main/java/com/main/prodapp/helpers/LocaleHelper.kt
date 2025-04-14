package com.main.prodapp.helpers

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

object LocaleHelper {

    private val preferenceName = "localization_prefs"
    private val englishOrNot = "is_english"

    fun isEnglish(context: Context): Boolean {
        val prefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        return prefs.getBoolean(englishOrNot, true)
    }

    fun setEnglish(context: Context) {
        val prefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(englishOrNot, true).apply()

    }

    fun setKorean(context: Context) {
        val prefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(englishOrNot, false).apply()
    }


    fun updateLocale(context: Context, language: String, country:String): Context{
        val locale = Locale(language, country)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)

        config.setLocale(locale)

        return context.createConfigurationContext(config)



    }
}