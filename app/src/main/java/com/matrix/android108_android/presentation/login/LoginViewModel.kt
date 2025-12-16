package com.matrix.android108_android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrix.android108_android.api.AuthService
import com.matrix.android108_android.api.LoginRequest
import com.matrix.android108_android.api.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val retrofit = RetrofitClient.retrofit

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(username: String, password:String){
        viewModelScope.launch {
            try{
                val response = retrofit.create(AuthService::class.java).login(LoginRequest(username, password))

                if(response.isSuccessful&&response.body()!=null){
                    val body = response.body()!!

                    RetrofitClient.accessToken = body.accessToken
                    RetrofitClient.refreshToken = body.refreshToken

                    _loginResult.postValue(true)
                }
                else{
                    _loginResult.postValue(false)
                }

            }catch (e: Exception){
                _loginResult.postValue(false)
            }
        }
    }


}