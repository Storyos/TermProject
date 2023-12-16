package com.example.termproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginForm : AppCompatActivity(){

    private lateinit var editTextLoginId : EditText
    private lateinit var editTextLoginPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var dataManager: MyDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_form)

        editTextLoginId = findViewById(R.id.editTextLoginId)
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        dataManager = MyDataManager(this)

        btnLogin.setOnClickListener{
            login()
        }
    }
    private fun login() {
        val enteredId = editTextLoginId.text.toString()
        val enteredPwd = editTextLoginPassword.text.toString()
        dataManager.open()
        while(true) {
            if (dataManager.loginUser(enteredId, enteredPwd)) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                //추가행동 필요
                break;
            } else {
                //로그인 실패
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}