package com.matrix.android108_android.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.matrix.android108_android.api.AuthService
import com.matrix.android108_android.api.RetrofitClient
import com.matrix.android108_android.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

//
class ProfileFragment : Fragment() {
   private lateinit var binding: FragmentProfileBinding
   lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentProfileBinding.inflate(inflater,container,false)
    return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.loadUser()
        observers()

    }

    fun observers(){

        viewModel.profile.observe(viewLifecycleOwner){
            profile->
            binding.firstNameTxt.text = profile.firstName
            binding.lastNameTxt.text = profile.lastName
            binding.usernameTxt.text = profile.username
            binding.emailTxt.text = profile.email
            binding.phoneTxt.text = profile.phone

            Glide.with(this)
                .load(profile.image)
                .into(binding.profilePicture)
        }


    }

   }