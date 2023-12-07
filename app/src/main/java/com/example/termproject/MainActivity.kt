package com.example.termproject

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.termproject.databinding.ActivityMainBinding
import com.kakao.util.maps.helper.Utility
import net.daum.mf.map.api.MapView
import java.lang.Exception
import java.security.MessageDigest
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mapView = MapView(this)
        binding.clKakaoMapView.addView(mapView)

        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }



}
