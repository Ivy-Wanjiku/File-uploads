package com.wanjiku.filesuploads.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.wanjiku.filesuploads.R
import com.wanjiku.filesuploads.databinding.ActivityMainBinding
import com.wanjiku.filesuploads.utils.Constance
import com.wanjiku.filesuploads.viewModel.PhotosViewModel


//class MainActivity : AppCompatActivity() {
//    val photoViewModel:PhotosViewModel by viewModels()
//    lateinit var binding:ActivityMainBinding
//    lateinit var pickMedia:ActivityResultLauncher<PickVisualMediaRequest>
//    var photoUri: Uri? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding=ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri->
//            if(uri !=null){
//                photoUri=uri
//                //do upload
//
//            }
//
//        }
//        binding.btnUpload.setOnClickListener{
//            validateForm()
//        }
//
//        binding.ivPhoto.setOnClickListener{
//            val mimeType= "image/*"
//            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
//        }
//        photoViewModel.errorLiveData.observe(this){ error->
//           Toast.makeText(this,error,Toast.LENGTH_LONG)
//
//        }
//        photoViewModel.photoResponseLiveData.observe(this){photoResponse->
//            val imageUri="${Constance.BASEURL}${photoResponse.image}"
//            Picasso.get()
//                .load(imageUri)
//                .into(binding.ivPhoto)
//        }
//    }
//
//    private fun validateForm(){
//        var err = false
//        if(photoUri==null){
//            err = true
//            Toast.makeText(this,"Please select photo", Toast.LENGTH_LONG).show()
//        }
//
//        val caption = binding.etCaption.text.toString()
//        if(caption.isBlank()){
//            err = true
//            binding.etCaption.error = "Caption required"
//        }
//        if (err){
//            photoViewModel.postPhoto(photoUri!!,caption)
//        }
//    }
//}
class MainActivity : AppCompatActivity() {
    val photoViewModel: PhotosViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register the ActivityResultLauncher here
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                photoUri = uri
                // Do upload or other tasks with the photoUri
            }
        }

        binding.ivPhoto.setOnClickListener {
            val mimeType = "image/*"
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)
                )
            )
        }

        binding.btnUpload.setOnClickListener {
            validateForm()
        }

        photoViewModel.errorLiveData.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        photoViewModel.photoResponseLiveData.observe(this) { photoResponse ->
            val imageUri = "${Constance.BASEURL}${photoResponse.image}"
            Picasso.get()
                .load(imageUri)
                .into(binding.ivPhoto)
        }
    }

    override fun onResume() {
        super.onResume()
        // Other onResume related code here if necessary
    }

    private fun validateForm() {
        var err = false
        if (photoUri == null) {
            err = true
            Toast.makeText(this, "Please select photo", Toast.LENGTH_LONG).show()
        }

        val caption = binding.etCaption.text.toString()
        if (caption.isBlank()) {
            err = true
            binding.etCaption.error = "Caption required"
        }

        if (!err) {
            photoViewModel.postPhoto(photoUri!!, caption)
        }
    }
}

