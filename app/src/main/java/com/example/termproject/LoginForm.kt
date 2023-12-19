package com.example.termproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginForm : AppCompatActivity() {

    private lateinit var editTextLoginId: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var dataManager: MyDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_form)

        editTextLoginId = findViewById(R.id.editTextLoginId)
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        dataManager = MyDataManager(this)

        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val enteredId = editTextLoginId.text.toString()
        val enteredPwd = editTextLoginPassword.text.toString()
        dataManager.open()
            if (dataManager.loginUser(enteredId, enteredPwd)) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                onLoginSuccess(enteredId)
                //추가행동 필요
            } else {
                //로그인 실패
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
    }
        private fun onLoginSuccess(userId: String) {
            val sharedPref = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("loggedInUserId", userId)
                apply()
            }

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
            finish()
        }
}