package com.wanjiku.filesuploads.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotosViewModel :ViewModel() {
    fun postPhoto(uri: Uri,caption:String){
        viewModelScope.launch {

        }
    }
}