package com.matrix.android108_android.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matrix.android108_android.R
import com.matrix.android108_android.api.AuthService
import com.matrix.android108_android.api.LoginRequest
import com.matrix.android108_android.api.RetrofitClient
import com.matrix.android108_android.databinding.FragmentLoginBinding
import com.matrix.android108_android.presentation.product.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.GONE

        binding.username.setText("emilys")
        binding.password.setText("emilyspass")
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginButton.setOnClickListener {
            viewModel.login(binding.username.text.toString(), binding.password.text.toString())
            observers()
        }
    }

    fun observers(){
        viewModel.loginResult.observe(viewLifecycleOwner){
            success->
            if(success){
                val sharedPref = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)

                sharedPref.edit{
                    putBoolean("isLogged", true)
                    putString("access_token", RetrofitClient.accessToken)
                    putString("refresh_token", RetrofitClient.refreshToken)
                }
            }else{
                Toast.makeText(requireContext(), "Login Failed",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}

