package com.wanjiku.filesuploads.api

import com.wanjiku.filesuploads.model.LoginRequest
import com.wanjiku.filesuploads.model.LoginResponse
import com.wanjiku.filesuploads.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @Multipart
    @POST("/selfie")
    suspend fun uploadPhotos(
        @Part caption:MultipartBody.Part,
        @Part image:MultipartBody.Part,


        ):Response<PhotoResponse>

@POST("/auth/login")
suspend fun  login(@Body loginRequest: LoginRequest):Response<LoginResponse>
}
