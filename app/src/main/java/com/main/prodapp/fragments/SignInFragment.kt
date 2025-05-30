package com.main.prodapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.main.prodapp.R
import com.main.prodapp.database.CharacterRepo
import com.main.prodapp.database.Task
import com.main.prodapp.database.TodoData
import com.main.prodapp.database.TodoListDatabase
import com.main.prodapp.database.UserData
import com.main.prodapp.databinding.FragmentSignInBinding
import com.main.prodapp.helpers.FirebaseService
import com.main.prodapp.helpers.LocaleHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class SignInFragment : Fragment(), View.OnClickListener {
    private var _binding : FragmentSignInBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseService.auth

        binding.signInButton.setOnClickListener(this)
        binding.moveToSignUpButton.setOnClickListener(this)

        binding.signInEnglishButton.setOnClickListener{

            LocaleHelper.setEnglish(requireContext())
            LocaleHelper.updateLocale(requireContext(), language = "en", country = "US")

            requireActivity().recreate()

        }

        binding.signInKoreanButton.setOnClickListener{
            LocaleHelper.setKorean(requireContext())
            LocaleHelper.updateLocale(requireContext(), language = "ko", country = "KR")

            requireActivity().recreate()

        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("SIGN", "START")

        val currentUser = auth.currentUser

        if (currentUser != null) {

            viewLifecycleOwner.lifecycleScope.launch {
                val userData = FirebaseService.getUserData()

                Log.d("SIGN", userData.toString())

                CharacterRepo.xp = userData?.xp ?: 0
                CharacterRepo.level = userData?.level ?: 1

                findNavController().navigate(R.id.show_inbox)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.signInButton.id -> {
                signIn(binding.emailField.text.toString(), binding.passwordField.text.toString())
            }
            else -> {
                findNavController().navigate(R.id.show_sign_up_page)
            }
        }
    }

    private fun signIn(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context,"Email or Password Empty",Toast.LENGTH_SHORT,).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {


                        LocaleHelper.setEnglish(requireContext())
                        requireActivity().recreate()
                        loadUserData()
                    } else {
                        Toast.makeText(context,"Authentication failed.",Toast.LENGTH_SHORT,).show()
                    }
                }
        }
    }

    private fun loadUserData() {
        val database = TodoListDatabase.getInstance(requireActivity())
        Log.d("SIGN", "Begin Load")
        viewLifecycleOwner.lifecycleScope.launch {

            val userData = FirebaseService.getUserData()

            CharacterRepo.xp = userData?.xp ?: 0
            CharacterRepo.level = userData?.level ?: 1

            Log.d("SIGN", userData.toString())

            FirebaseService.createUserData(UserData())
            val tasks = FirebaseService.getCurrentUserTasks()


            for (task in tasks) {
                var filePath: String? = null
                if (task.imagePath != null) {
                    FirebaseService.loadImage(requireContext(), task.imagePath.toString())
                    filePath = File(requireContext().filesDir, task.imagePath!!).toString()
                    delay(300L) // HOTFIX
                }

                val todo = TodoData(taskID = task.id ?: "none",
                    title = task.title ?: "none",
                    description = task.description ?: "none",
                    imagePath = filePath
                )
                database.todoListDao().insertTodo(todo)
                Log.d("SIGN", "Insert")
            }

            Log.d("SIGN", "Switching")
            findNavController().navigate(R.id.show_inbox)
        }
    }
}