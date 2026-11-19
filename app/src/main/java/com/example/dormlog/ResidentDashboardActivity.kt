package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityResidentDashboardBinding

class ResidentDashboardActivity : AppCompatActivity() {
    private lateinit var b: ActivityResidentDashboardBinding
    private val adapter = VisitAdapter(
        onCheckOut = { _ -> /* Residents cannot check out */ },
        onOpen = { id ->
            startActivity(Intent(this, VisitDetailsActivity::class.java).putExtra("visitId", id))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityResidentDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

        val user = AuthRepository.currentUser
        b.tvTitle.text = "Welcome, ${user?.residentName} (Room ${user?.roomNumber})"

        b.rvVisits.layoutManager = LinearLayoutManager(this)
        b.rvVisits.adapter = adapter

        b.btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        b.btnLogout.setOnClickListener {
            AuthRepository.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val user = AuthRepository.currentUser
        val myVisitors = VisitRepository.getActive().filter { 
            it.residentName.equals(user?.residentName, true) 
        }
        adapter.submit(myVisitors)
    }
}