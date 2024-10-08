package com.wanjiku.filesuploads.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wanjiku.filesuploads.R
import com.wanjiku.filesuploads.databinding.ActivityLogInBinding
import com.wanjiku.filesuploads.model.LoginRequest
import com.wanjiku.filesuploads.model.LoginResponse
import com.wanjiku.filesuploads.utils.Constance
import com.wanjiku.filesuploads.viewModel.LoginViewModel
import com.wanjiku.filesuploads.viewModel.PhotosViewModel

class LogInActivity : AppCompatActivity() {
    val loginViewModel: LoginViewModel by viewModels()
    lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogInBinding.inflate(layoutInflater)
        redirectUsers()
        setContentView(binding.root)


    }
    fun redirectUsers(){
        val sharedPreferences=getSharedPreferences(Constance.PREFS, Context.MODE_PRIVATE)
        val token=sharedPreferences.getString(Constance.ACCESS_TOKEN,"")
        if (token!!.isBlank()){
            startActivity(Intent(this,PhotoCapture::class.java))
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        binding.btnLogin.setOnClickListener { validateLogin() }
        loginViewModel.errorLiveData.observe(this){
            error->
            Toast.makeText(this,error,Toast.LENGTH_LONG).show()

        }
        loginViewModel.loginLiveData.observe(this){loginResponse->
            Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()
            persistLogIn(loginResponse)
            startActivity(Intent(this,PhotoCapture::class.java))
            finish()
        }
    }
    private  fun  persistLogIn(loginResponse: LoginResponse){
//        val sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPreferences=getSharedPreferences(Constance.PREFS, Context.MODE_PRIVATE)

        val editor=sharedPreferences.edit()
        editor.putString(Constance.FIRST_NAME,loginResponse.firstName)
        editor.putString(Constance.LAST_NAME,loginResponse.lastName)
        editor.putString(Constance.ACCESS_TOKEN,loginResponse.accessToken)
        editor.apply()
    }
    private fun validateLogin(){
        val  username=binding.etUsername.text.toString()
        val password=binding.etPassword.text.toString()
        var error=false
        if (username.isBlank()){
            binding.tilUserName.error="UserName is required"
            error=true
        }
        if (password.isBlank()){
            binding.tilPassword.error="Password is required"
            error=true
        }
        if (!error){
            val  loginRequest=LoginRequest(username,password)
            loginViewModel.login(loginRequest)
        }
    }
}