package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityActiveVisitsBinding
import com.example.dormlog.model.UserRole

class ActiveVisitsActivity : AppCompatActivity() {

    private lateinit var b: ActivityActiveVisitsBinding
    private val adapter = VisitAdapter(
        onCheckOut = { id ->
            if (VisitRepository.checkOut(id)) {
                Toast.makeText(this, "Checked out", Toast.LENGTH_SHORT).show()
                refresh()
            } else {
                Toast.makeText(this, "Cannot check out", Toast.LENGTH_SHORT).show()
            }
        },
        onOpen = { id ->
            startActivity(Intent(this, VisitDetailsActivity::class.java)
                .putExtra("visitId", id))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityActiveVisitsBinding.inflate(layoutInflater)
        setContentView(b.root)

        val user = AuthRepository.currentUser
        if (user?.role == UserRole.RESIDENT) {
            b.btnNewVisit.visibility = View.GONE
        }

        b.rvVisits.layoutManager = LinearLayoutManager(this)
        b.rvVisits.adapter = adapter

        b.btnNewVisit.setOnClickListener {
            startActivity(Intent(this, NewVisitActivity::class.java))
        }
    }

    override fun onResume() { super.onResume(); refresh() }

    private fun refresh() {
        val user = AuthRepository.currentUser
        var list = VisitRepository.getActive()

        if (user?.role == UserRole.RESIDENT) {
            list = list.filter {
                it.residentName.equals(user.residentName, ignoreCase = true)
            }
        }

        adapter.submit(list)
        b.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}