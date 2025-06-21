package com.pemrograman_platform.karareserve.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pemrograman_platform.karareserve.MainActivity
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var customToast: Toast
    private lateinit var auth: FirebaseAuth
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.registerRoute.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email = binding.valueEmail.text.toString().trim()
            val password = binding.valuePassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and Password can't be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.progressContainer.visibility = View.VISIBLE
            binding.loginButton.isEnabled = false

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    binding.progressContainer.visibility = View.GONE
                    binding.loginButton.isEnabled = true

                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to Login: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        val layout = layoutInflater.inflate(R.layout.custom_toast, findViewById(android.R.id.content), false)

        customToast = Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            customToast.cancel()
            super.onBackPressed()
        } else {
            customToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}