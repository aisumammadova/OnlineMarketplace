package com.matrix.android108_android.presentation.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrix.android108_android.api.AuthService
import com.matrix.android108_android.api.RetrofitClient
import com.matrix.android108_android.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class ProfileViewModel: ViewModel() {

    private val authService = RetrofitClient.retrofit.create(AuthService::class.java)
    private val _profile = MutableLiveData<User>()
    val profile: LiveData<User> = _profile




    fun loadUser(){
        viewModelScope.launch(Dispatchers.IO) {

                val response = authService.getUserProfile()

                if(response.isSuccessful&&response.body()!=null){
                    _profile.postValue(response.body())
                }

        }
    }

}