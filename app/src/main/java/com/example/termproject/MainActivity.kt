package com.example.termproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.termproject.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapView
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import net.daum.mf.map.api.MapPoint
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //화면전환 버튼
        val buttonToLoginForm = findViewById<Button>(R.id.buttontologin)
        buttonToLoginForm.setOnClickListener {
            try{val intent2 = Intent(this, LoginForm::class.java)
            startActivity(intent2)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Login Form 오류", Toast.LENGTH_SHORT).show()
            }
        }

        //회원가입 버튼
        val buttonToJoinForm = findViewById<Button>(R.id.buttontojoin)
        buttonToJoinForm.setOnClickListener{
            val intent = Intent(this, JoinForm::class.java)
            startActivity(intent)
        }
    }

}
