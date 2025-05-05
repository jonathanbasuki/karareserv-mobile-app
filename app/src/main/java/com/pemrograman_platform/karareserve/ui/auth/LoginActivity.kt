package com.pemrograman_platform.karareserve.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pemrograman_platform.karareserve.MainActivity
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var customToast: Toast

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Route to register activity
        binding.registerRoute.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Proceed login while ui shows circular progress bar
        binding.loginButton.setOnClickListener {
            binding.progressContainer.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }

        // Custom toast when user click double back
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, findViewById(android.R.id.content), false)

        customToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT)
        customToast = Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
        }
    }

    // Override method when user click back
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