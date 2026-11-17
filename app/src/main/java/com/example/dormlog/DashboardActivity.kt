package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.databinding.ActivityDashboardBinding
import com.example.dormlog.model.UserRole

class DashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

        val user = AuthRepository.currentUser

        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        when (user.role) {
            UserRole.RESIDENT -> {
                b.tvWelcome.text = "Resident: ${user.residentName}"
                b.btnNew.visibility = View.GONE // Residents cannot check people in
            }
            UserRole.STAFF -> {
                b.tvWelcome.text = "Staff Dashboard"
                b.btnNew.visibility = View.VISIBLE
            }
            UserRole.ADMIN -> {
                b.tvWelcome.text = "Admin Dashboard"
                b.btnNew.visibility = View.VISIBLE
            }
        }

        b.btnActive.setOnClickListener {
            startActivity(Intent(this, ActiveVisitsActivity::class.java))
        }
        b.btnNew.setOnClickListener {
            startActivity(Intent(this, NewVisitActivity::class.java))
        }
        b.btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}