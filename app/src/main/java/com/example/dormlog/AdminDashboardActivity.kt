package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

        val user = AuthRepository.currentUser
        b.tvHeader.text = "Admin Dashboard - ${user?.email ?: ""}"

        b.btnActive.setOnClickListener { startActivity(Intent(this, ActiveVisitsActivity::class.java)) }
        b.btnHistory.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }

        b.btnReports.setOnClickListener {
            val active = VisitRepository.getActive().size
            val history = VisitRepository.getHistory().size
            val total = active + history
            Toast.makeText(this, "System Reports:\nTotal Visits: $total\nActive Now: $active\nPast History: $history", Toast.LENGTH_LONG).show()
        }

        b.btnLogout.setOnClickListener {
            AuthRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}