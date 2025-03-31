package com.main.prodapp.database

import androidx.lifecycle.viewmodel.viewModelFactory
import com.main.prodapp.helpers.FirebaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object CharacterRepo {

    private const val XP_MAX = 100

    var xp: Int = 0
    var level: Int = 1

    fun increaseXpLevel(xpIncrease: Int){
        xp += xpIncrease
        if (xp >= XP_MAX){
            xp -= XP_MAX
            level++

            // This is not a good implementation.
            CoroutineScope(Dispatchers.IO).launch {
                saveData()
            }
        }
    }

    fun getCharacterDataAsMap(): HashMap<String, Any>{
        val hashMap = HashMap<String, Any>()
        hashMap["xp"] = xp
        hashMap["level"] = level

        return hashMap
    }

    private suspend fun saveData() {
        FirebaseService.updateGameData(getCharacterDataAsMap())
    }

}