package com.wanjiku.filesuploads.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wanjiku.filesuploads.R
import com.wanjiku.filesuploads.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var pickMedia:ActivityResultLauncher<PickVisualMediaRequest>
    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){url->
            if(Uri !=null){
                photoUri=Uri
                //do upload

            }
        }
        binding.btnUpload.setOnClickListener{
            validateForm()
        }

        binding.ivPhoto.setOnClickListener{
            val mimeType= "image/*"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
        }
    }

    private fun validateForm(){
        var err = false
        if(photoUri==null){
            err = true
            Toast.makeText(this,"Please select photo", Toast.LENGTH_LONG).show()
        }

        val caption = binding.etCaption.text.toString()
        if(caption.isBlank()){
            err = true
            binding.etCaption.error = "Caption required"
        }
        if (err){
            photoViewModel.post
        }
    }
}

