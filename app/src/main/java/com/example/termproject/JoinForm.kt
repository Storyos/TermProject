package com.example.termproject

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JoinForm : AppCompatActivity(){

    private lateinit var Username: EditText
    private lateinit var Pwd : EditText
    private lateinit var btn_Join : Button
    private lateinit var Nickname : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_form)

        Nickname = findViewById(R.id.editTextNickname)
        Username = findViewById(R.id.editTextUserName)
        Pwd = findViewById(R.id.editTextPassword)
        btn_Join = findViewById(R.id.buttonJoin)

        btn_Join.setOnClickListener{
            signUp()
        }
    }
    private fun signUp() {
        val nickname = Nickname.text.toString()
        val userId = Username.text.toString()
        val password = Pwd.text.toString()
        if (userId.isNotEmpty() && password.isNotEmpty()) {
            val dataManager = MyDataManager(this)
            dataManager.open()

            val values = ContentValues()
            values.put("nickname",nickname)
            values.put("userId", userId)
            values.put("password", password)

            dataManager.insertData(nickname,userId, password)
            dataManager.close()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "다시 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}