package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.databinding.ActivityStaffDashboardBinding

class StaffDashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityStaffDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityStaffDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnNew.setOnClickListener { startActivity(Intent(this, NewVisitActivity::class.java)) }
        b.btnActive.setOnClickListener { startActivity(Intent(this, ActiveVisitsActivity::class.java)) }
        b.btnHistory.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }

        b.btnLogout.setOnClickListener {
            AuthRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}