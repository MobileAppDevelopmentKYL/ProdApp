package com.main.prodapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.main.prodapp.databinding.FragmentSignInBinding

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

        auth = Firebase.auth

        binding.signInButton.setOnClickListener(this)
        binding.signUpTitle.setOnClickListener(this)
    }

    //TODO: Check if the user is already login
    override fun onStart() {
        super.onStart()

        // Check if the user is already login

    }

    //TODO: Sign Up Button
    override fun onClick(v: View) {
        when (v.id) {
            binding.signInButton.id -> {
                signIn(binding.emailField.text.toString(), binding.passwordField.text.toString())
            }
            else -> Log.e("SignInFragment", "Error: Invalid button press")
        }
    }

    // TODO: Check for null Strings
    // TODO: Switch Screens
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Authentication success.",Toast.LENGTH_SHORT,).show()
                } else {
                    Toast.makeText(context,"Authentication failed.",Toast.LENGTH_SHORT,).show()
                }
            }
    }
}