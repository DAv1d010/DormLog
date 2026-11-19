package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.databinding.ActivityLoginBinding
import com.example.dormlog.model.UserRole

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnLogin.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = AuthRepository.login(this, email, pass)

            if (user != null) {
                Toast.makeText(this, "Welcome ${user.role.name}", Toast.LENGTH_SHORT).show()

                when (user.role) {
                    UserRole.RESIDENT -> startActivity(Intent(this, ResidentDashboardActivity::class.java))
                    UserRole.STAFF -> startActivity(Intent(this, StaffDashboardActivity::class.java))
                    UserRole.ADMIN -> startActivity(Intent(this, AdminDashboardActivity::class.java))
                }
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password (use 123456)", Toast.LENGTH_LONG).show()
            }
        }

        b.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}