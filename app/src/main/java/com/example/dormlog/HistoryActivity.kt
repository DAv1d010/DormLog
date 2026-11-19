package com.example.dormlog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityHistoryBinding
import com.example.dormlog.model.UserRole

class HistoryActivity : AppCompatActivity() {

    private lateinit var b: ActivityHistoryBinding
    private val adapter = VisitAdapter(
        onCheckOut = { _ -> },
        onOpen = { id ->
            startActivity(Intent(this, VisitDetailsActivity::class.java)
                .putExtra("visitId", id))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.rvVisits.layoutManager = LinearLayoutManager(this)
        b.rvVisits.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val user = AuthRepository.currentUser
        var list = VisitRepository.getHistory()
        if (user?.role == UserRole.RESIDENT) {
            list = list.filter { it.residentName.equals(user.residentName, true) }
        }
        adapter.submit(list)
        b.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}