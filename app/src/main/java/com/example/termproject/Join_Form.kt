package com.example.termproject

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Join_Form : AppCompatActivity(){

    private lateinit var Username: EditText
    private lateinit var Pwd : EditText
    private lateinit var btn_Join : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.join_form)

        Username = findViewById(R.id.editTextUsername)
        Pwd = findViewById(R.id.editTextPassword)
        btn_Join = findViewById(R.id.btnSignUp)

        btn_Join.setOnClickListener{
            signUp()
        }
    }
    private fun signUp() {
        val username = Username.text.toString()
        val password = Pwd.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val dataManager = MyDataManager(this)
            dataManager.open()

            val values = ContentValues()
            values.put("username", username)
            values.put("password", password)

            dataManager.insertData(username, password)
            dataManager.close()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(applicationContext, "다시 입력해주세요", Toast.LENGTH_SHORT)
        }
    }
}