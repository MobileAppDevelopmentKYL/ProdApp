package com.main.prodapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.main.prodapp.R
import com.main.prodapp.databinding.FragmentInboxBinding
import com.main.prodapp.databinding.FragmentProfileBinding

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCharacter.setOnClickListener {

            findNavController().navigate(R.id.transfer_to_character)

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