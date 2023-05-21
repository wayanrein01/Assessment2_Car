package org.d3if2085.assessment2.ui.login

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
import org.d3if2085.assessment2.databinding.FragmentLoginBinding
import org.d3if2085.assessment2.repository.UserRepository
import org.d3if2085.assessment2.viewmodel.UserViewModel
import org.d3if2085.assessment2.viewmodel.factory.UserViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButton()
    }

    private fun initButton() {
        binding.btnLogin.setOnClickListener { login() }
        binding.toRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun isLogin(user: User, password: String): Boolean {
        if (user.password != password) return false
        return true
    }

    private fun login() {
        val username = binding.inputUsernameLogin.text.toString()
        val password = binding.inputPasswordLogin.text.toString()
        if (checkInput(username, password)) {
            Toast.makeText(requireContext(), "username and password is required", Toast.LENGTH_LONG)
                .show()
            return
        }
        viewModel.getUserByUsername(username)
            .observe(requireActivity()) {
                if (it == null) {
                    Toast.makeText(requireContext(), "Account not found", Toast.LENGTH_LONG)
                        .show()
                    return@observe
                }

                val isLogin = isLogin(it, password)
                if (!isLogin) {
                    Toast.makeText(requireContext(), "Auth failed", Toast.LENGTH_LONG).show()
                    return@observe
                }

                Toast.makeText(requireContext(), "Auth success", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
    }

    private fun checkInput(username: String, password: String): Boolean {
        return (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
    }
}