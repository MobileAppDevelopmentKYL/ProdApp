package com.main.prodapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.main.prodapp.R
import com.main.prodapp.database.CharacterRepo
import com.main.prodapp.databinding.FragmentCharacterBinding
import kotlin.random.Random

private const val TAG = "CharacterFragment"

class CharacterFragment : Fragment() {
    private var _binding : FragmentCharacterBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Start onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Start onCreateView")
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = CharacterRepo.getCharacterDataAsMap()
        val levelName = getString(R.string.character_level)
        val xpName = getString(R.string.character_xp)
        val health = getString(R.string.character_health)
        val strength = getString(R.string.character_strength)
        binding.characterLevel.text = levelName + ": ${data["level"]}"
        binding.characterXp.text = xpName + ": ${data["xp"]}/100"
        binding.characterHealth.text = health + ": ${data["level"] as Int * 12}"
        binding.characterStrength.text = strength + ": ${data["level"] as Int * 15}"


        binding.adventureButton.setOnClickListener{
            val outcome = adventureStart()

            CharacterRepo.increaseXpLevel(outcome.xpChange)

            if(binding.adventureOutcome.visibility == View.GONE){
                binding.adventureOutcome.visibility = View.VISIBLE
            }

            if(binding.adventureDetail.visibility == View.GONE) {
                binding.adventureDetail.visibility = View.VISIBLE
            }

            binding.adventureOutcome.text = outcome.message
            val gained = getString(R.string.character_gained_text)
            binding.adventureDetail.text = gained + " ${outcome.xpChange} XP!"

            refreshPage()
        }
    }

    private fun refreshPage(){

        val charData = CharacterRepo.getCharacterDataAsMap()
        val level = charData["level"] as Int
        val xp = charData["xp"] as Int

        val levelName = getString(R.string.character_level)
        val xpName = getString(R.string.character_xp)
        val health = getString(R.string.character_health)
        val strength = getString(R.string.character_strength)

        binding.characterLevel.text = levelName + ": ${level}"
        binding.characterXp.text = xpName + ": ${charData["xp"]}/100"
        binding.characterHealth.text = health + ": ${level * 12}"
        binding.characterStrength.text = strength + ": ${level * 15}"

    }


    private fun adventureStart(): Adventure{

        val adventureDragon = getString(R.string.adventure_dragon)
        val adventureGoblin = getString(R.string.adventure_goblin)
        val adventureGold = getString(R.string.adventure_gold)
        val adventureUseless = getString(R.string.adventure_useless)

        val outcomes = listOf(
            Adventure(adventureDragon, Random.nextInt(5, 15)),
            Adventure(adventureGoblin, Random.nextInt(-10, -5)),
            Adventure(adventureGold, Random.nextInt(10, 20)),
            Adventure(adventureUseless, Random.nextInt(-5, 0))
        )

        return outcomes.random()
    }

    data class Adventure(
        val message: String,
        val xpChange: Int
    )

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "Start onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "Start onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "Start onPause")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "Start onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "Start onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Start onDestoryView")
    }
}