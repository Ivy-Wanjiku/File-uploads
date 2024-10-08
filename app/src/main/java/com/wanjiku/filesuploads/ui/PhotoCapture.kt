package com.wanjiku.filesuploads.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import com.wanjiku.filesuploads.R
import com.wanjiku.filesuploads.databinding.ActivityPhotoCaptureBinding
import java.io.File

class PhotoCapture : AppCompatActivity() {
    lateinit var photoFile:File

    var cameraLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val capturedPhoto = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.ivPhoto2.setImageBitmap(capturedPhoto)

        }
    }
    lateinit var  binding: ActivityPhotoCaptureBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val locationRequest=registerForActivityResult(ActivityResultContracts.RequestPermission()){result->
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED){
            getLocationUpdates()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPhotoCaptureBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
    }
//    var locationREquest=registerRe
    private  fun getPhotoFile(filename:String):File{
        val  storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename,"jpg",storageDir)
    }

    override fun onResume() {
        super.onResume()
        binding.btnCapture.setOnClickListener {
            val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile=getPhotoFile("photo_${System.currentTimeMillis()}")
            val fileProvider=FileProvider.getUriForFile(this,"com.wanjiku.filesuploads.provider",photoFile)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider)
            cameraLauncher.launch(cameraIntent)
        }
        getLocationUpdates()
    }
    private  fun getLocationUpdates(){
       if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
           ==PackageManager.PERMISSION_GRANTED) {
           val locationRequest=com.google.android.gms.location.LocationRequest
               .Builder(10000)
               .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
               .build()
           val locationCallback=object :LocationCallback(){
               override fun onLocationResult(p0: LocationResult) {
                   super.onLocationResult(p0)
                   val lastLocation=p0.lastLocation
                   if (lastLocation!=null){
                       Toast.makeText(this@PhotoCapture,
                           "${lastLocation.latitude},${lastLocation.latitude}",
                           Toast.LENGTH_LONG).show()
                   }
               }
           }
           fusedLocationProviderClient.requestLocationUpdates(
               locationRequest,
               locationCallback,
               Looper.getMainLooper()
           )
       }else{
           locationRequest.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)

       }
    }
}