package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnLogin.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString()

            when {
                email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    b.etEmail.error = "Enter a valid email"
                pass.length < 6 ->
                    b.etPassword.error = "Password must be at least 6 characters"
                else -> {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }
        }
    }
}