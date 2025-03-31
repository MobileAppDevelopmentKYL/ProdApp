package com.main.prodapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.main.prodapp.database.CharacterRepo
import com.main.prodapp.databinding.FragmentCharacterBinding
import kotlin.random.Random









private const val TAG = "CharacterFragment"

class CharacterFragment : Fragment() {
    private var _binding : FragmentCharacterBinding? = null

    private lateinit var characterRepo : CharacterRepo



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
        binding.characterLevel.text = "Level: ${data["level"]}"
        binding.characterXp.text = "XP: ${data["xp"]}/100"
        binding.characterHealth.text = "Health: ${data["level"] as Int * 12}"
        binding.characterStrength.text = "Strength: ${data["level"] as Int * 15}"


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
            binding.adventureDetail.text = "You gained ${outcome.xpChange} XP!"

            refreshPage()


        }
    }

    private fun refreshPage(){

        val charData = CharacterRepo.getCharacterDataAsMap()
        val level = charData["level"] as Int
        val xp = charData["xp"] as Int

        binding.characterLevel.text = "Level: ${level}"
        binding.characterXp.text = "XP: ${charData["xp"]}/100"
        binding.characterHealth.text = "Health: ${level * 12}"
        binding.characterStrength.text = "Strength: ${level * 15}"

    }


    private fun adventureStart(): Adventure{
        val outcomes = listOf(
            Adventure("You fought a dragon and won!", Random.nextInt(5, 15)),
            Adventure("You fought a goblin and lost! You are useless!", Random.nextInt(-10, -5)),
            Adventure("You fought a gold mine! Yay :)", Random.nextInt(10, 20)),
            Adventure("You achieved nothing and was useless!", Random.nextInt(-5, 0))

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