package com.main.prodapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.main.prodapp.R
import com.main.prodapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(), View.OnClickListener {
    private var _binding : FragmentSignUpBinding? = null
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
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.signUpButton.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.signUpButton.id -> {
                signUp(binding.emailField.text.toString(), binding.passwordField.text.toString(),
                    binding.confirmPasswordField.text.toString())
            }
            else -> Log.e("Error", "Sign Up went Wrong.")
        }
    }

    private fun signUp(email: String, password: String, confirmPassword: String) {

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "All fields must be filled in.", Toast.LENGTH_SHORT).show()
        }
        else if (password == confirmPassword) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.show_inbox)
                    } else {
                        Toast.makeText(context, task.exception?.message.toString(), Toast.LENGTH_LONG,).show()
                    }
                }
        } else {
            Toast.makeText(context, "Passwords don't match.", Toast.LENGTH_SHORT).show()
        }
    }

}