package com.wanjiku.filesuploads.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.wanjiku.filesuploads.R
import com.wanjiku.filesuploads.databinding.FragmentSettingsFragmentsBinding
import com.wanjiku.filesuploads.utils.Constance

class SettingsFragments : Fragment() {
    var _binding:FragmentSettingsFragmentsBinding?=null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentSettingsFragmentsBinding.inflate(inflater,container,false)
        val view=binding.root
        return view

    }

    override fun onResume() {
        super.onResume()
        binding.btnLogOut.setOnClickListener {
            val dialog=AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out")
                .setPositiveButton("Yes",){dialog, which->
                    logOutUser()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){dialog,which->
                    dialog.dismiss()
                }
      dialog.show()
        }
    }
private fun logOutUser(){
    val prefs=requireContext().getSharedPreferences(Constance.PREFS,Context.MODE_PRIVATE)
    val editor=prefs.edit()
//    editor.clear()
    editor.remove(Constance.ACCESS_TOKEN)
    editor.remove(Constance.FIRST_NAME)
    editor.remove(Constance.LAST_NAME)
    editor.apply()
    startActivity(Intent(requireContext(),LogInActivity::class.java))
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }




}