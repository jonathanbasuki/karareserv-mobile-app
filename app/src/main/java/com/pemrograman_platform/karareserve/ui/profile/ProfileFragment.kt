package com.pemrograman_platform.karareserve.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pemrograman_platform.karareserve.R
import com.pemrograman_platform.karareserve.databinding.FragmentProfileBinding
import com.pemrograman_platform.karareserve.ui.auth.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        binding.textEmail.text = user?.email ?: "Unknown"

        binding.menuLogout.setOnClickListener {
            binding.progressContainer.visibility = View.VISIBLE

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "You're logged out!", Toast.LENGTH_SHORT).show()

            // Navigasi ke LoginActivity (atau activity lainnya)
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}