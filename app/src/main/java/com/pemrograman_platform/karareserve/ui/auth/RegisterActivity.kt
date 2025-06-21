package com.pemrograman_platform.karareserve.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.pemrograman_platform.karareserve.MainActivity
import com.pemrograman_platform.karareserve.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.loginRoute.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.registerButton.setOnClickListener {
            val email = binding.valueEmail.text.toString().trim()
            val password = binding.valuePassword.text.toString().trim()
            val confirmPassword = binding.valueConfirmPassword.text.toString().trim()

            if (password != confirmPassword) {
                Toast.makeText(this, "Password doesn't match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and Password can't be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.progressContainer.visibility = View.VISIBLE
            binding.registerButton.isEnabled = false

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    binding.progressContainer.visibility = View.GONE
                    binding.registerButton.isEnabled = true

                    if (task.isSuccessful) {
                        Toast.makeText(this, "Register successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to Register: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}