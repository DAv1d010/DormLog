package com.example.dormlog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var b: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnRegister.setOnClickListener {
            val email = b.etEmail.text.toString().trim()
            val pass = b.etPassword.text.toString()
            val name = b.etName.text.toString().trim()
            val room = b.etRoom.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty() || name.isEmpty() || room.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Resident email must end with @gmail.com", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = AuthRepository.registerResident(this, email, pass, name, room)
            if (success) {
                Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Email is already registered.", Toast.LENGTH_LONG).show()
            }
        }

        b.btnBack.setOnClickListener {
            finish()
        }
    }
}