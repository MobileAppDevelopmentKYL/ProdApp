package com.main.prodapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.main.prodapp.R
import com.main.prodapp.database.CharacterRepo
import com.main.prodapp.database.TodoListDatabase
import com.main.prodapp.databinding.FragmentSettingBinding
import com.main.prodapp.helpers.FirebaseService
import com.main.prodapp.helpers.LocaleHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SettingFragment"

class SettingFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: TodoListDatabase

    private var _binding : FragmentSettingBinding? = null
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
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        binding.signOut.setOnClickListener {
            database = TodoListDatabase.getInstance(requireContext())

            viewLifecycleOwner.lifecycleScope.launch {
                FirebaseService.updateGameData(CharacterRepo.getCharacterDataAsMap())
                delay(0L)
                database.todoListDao().deleteAll()

                auth.signOut()

                LocaleHelper.setEnglish(requireContext())
                requireActivity().recreate()
                findNavController().navigate(R.id.action_settingFragment_to_signInFragment)
            }
        }

        binding.signInKoreanButton.setOnClickListener{
            LocaleHelper.setKorean(requireContext())
            LocaleHelper.updateLocale(requireContext(), language = "ko", country = "KR")
            requireActivity().recreate()
        }

        binding.signInEnglishButton.setOnClickListener{

            LocaleHelper.setEnglish(requireContext())
            LocaleHelper.updateLocale(requireContext(), language = "en", country = "US")
            requireActivity().recreate()

        }
    }

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
        _binding = null
        Log.d(TAG, "Start onDestoryView")
    }
}