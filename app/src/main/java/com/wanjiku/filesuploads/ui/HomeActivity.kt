package com.wanjiku.filesuploads.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wanjiku.filesuploads.R
import com.wanjiku.filesuploads.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.setOnItemSelectedListener{menuItem->
            when (menuItem.itemId){
                R.id.home->{
                    loadFragment(HomeFragment())

                }
                R.id.games->{
                    loadFragment(GamesFragment())
                }
                R.id.profile->{
                    loadFragment(ProfileFRagment())
                }
                R.id.setings->{
                    loadFragment(SettingsFragments())
                }
                else->loadFragment(HomeFragment())
            }

        }
    }
    fun  loadFragment(fragment:Fragment):Boolean{
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fcvHome,fragment)
        fragmentTransaction.commit()
        return  true
    }
}