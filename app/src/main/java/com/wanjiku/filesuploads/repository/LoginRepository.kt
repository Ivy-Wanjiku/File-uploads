package com.wanjiku.filesuploads.repository

import com.wanjiku.filesuploads.api.ApiClient
import com.wanjiku.filesuploads.api.ApiInterface
import com.wanjiku.filesuploads.model.LoginRequest
import com.wanjiku.filesuploads.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository {
    val apiClient=ApiClient.buildClient(ApiInterface::class.java)
    suspend fun login(loginRequest: LoginRequest):Response<LoginResponse>{
        return  withContext(Dispatchers.IO){
            apiClient.login(loginRequest)
        }
    }
}