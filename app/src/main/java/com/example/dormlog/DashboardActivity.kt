package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

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