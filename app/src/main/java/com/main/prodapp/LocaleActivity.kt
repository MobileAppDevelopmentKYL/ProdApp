package com.main.prodapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.main.prodapp.helpers.LocaleHelper

open class LocaleActivity : AppCompatActivity(){

    override fun attachBaseContext(newBase: Context) {

        val language: String
        val country: String

        if(LocaleHelper.isEnglish(newBase)){
            language = "en"
            country = "US"
        }else{
            language = "ko"
            country = "KR"
        }


        val context = LocaleHelper.updateLocale(newBase, language, country)
        super.attachBaseContext(context)
    }
}