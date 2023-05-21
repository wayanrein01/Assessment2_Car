package org.d3if2085.assessment2.ui.register

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if2085.assessment2.R
import org.d3if2085.assessment2.database.RoomDb
import org.d3if2085.assessment2.database.entity.User
import org.d3if2085.assessment2.databinding.FragmentRegisterBinding
import org.d3if2085.assessment2.repository.UserRepository
import org.d3if2085.assessment2.viewmodel.UserViewModel
import org.d3if2085.assessment2.viewmodel.factory.UserViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: UserViewModel by lazy {
        val db = RoomDb.getInstance(requireContext())
        val repo = UserRepository(db.userDao)
        val factory = UserViewModelFactory(repo)
        ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButton()
    }

    private fun initButton() {
        binding.btnRegister.setOnClickListener { register() }
        binding.toLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun checkInput(name: String, username: String, password: String): Boolean {
        return (TextUtils.isEmpty(name) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
    }

    private fun register() {
        val name = binding.inputNameRegister.text.toString()
        val username = binding.inputUsernameRegister.text.toString()
        val password = binding.inputPasswordRegister.text.toString()

        if (checkInput(name, username, password)) {
            Toast.makeText(requireContext(), "Register not success", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.getUserByUsername(username).observe(requireActivity()) {
            if (it != null) {
                Toast.makeText(requireContext(), "Account is already", Toast.LENGTH_LONG)
                    .show()
                return@observe
            }

            val user = User(0, name, username, password)
            viewModel.insertUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_LONG)
                .show()
        }
    }
}