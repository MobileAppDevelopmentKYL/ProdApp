package com.main.prodapp.database

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit


object CharacterRepo{

    private const val XP_MAX = 100

    var xp: Int = 0
    var level: Int = 1




    fun increaseXpLevel(xpIncrease: Int){
        xp += xpIncrease
        if (xp >= XP_MAX){
            xp -= XP_MAX
            level++
        }
    }


    fun getCharacterDataAsMap(): HashMap<String, Any>{
        val hashMap = HashMap<String, Any>()
        hashMap.put("xp", xp)
        hashMap.put("level", level)

        return hashMap
    }

}