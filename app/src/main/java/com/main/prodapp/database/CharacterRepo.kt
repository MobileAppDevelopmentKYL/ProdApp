package com.main.prodapp.database




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