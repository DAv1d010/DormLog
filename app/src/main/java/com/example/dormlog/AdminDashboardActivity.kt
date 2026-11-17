package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnActive.setOnClickListener { startActivity(Intent(this, ActiveVisitsActivity::class.java)) }
        b.btnHistory.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }

        b.btnReports.setOnClickListener {
            Toast.makeText(this, "Reports module coming in Phase 3", Toast.LENGTH_SHORT).show()
        }

        b.btnLogout.setOnClickListener {
            AuthRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}