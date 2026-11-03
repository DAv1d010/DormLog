package com.example.dormlog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityVisitDetailsBinding
import com.example.dormlog.model.VisitStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VisitDetailsActivity : AppCompatActivity() {

    private lateinit var b: ActivityVisitDetailsBinding
    private val fmt = SimpleDateFormat("hh:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityVisitDetailsBinding.inflate(layoutInflater)
        setContentView(b.root)

        val id = intent.getStringExtra("visitId")
        if (id == null) { finish(); return }

        render(id)

        b.btnCheckOut.setOnClickListener {
            if (VisitRepository.checkOut(id)) {
                Toast.makeText(this, "Checked out", Toast.LENGTH_SHORT).show()
                render(id)
            } else {
                Toast.makeText(this, "Cannot check out", Toast.LENGTH_SHORT).show()
            }
        }

        b.btnDelete.setOnClickListener {
            if (VisitRepository.delete(id)) {
                Toast.makeText(this, "Visit deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun render(id: String) {
        val v = VisitRepository.findById(id) ?: return

        b.tvVisitor.text = v.visitorName
        b.tvResident.text = "${v.residentName} · ${v.roomNumber}"
        b.tvPurpose.text = v.purpose
        b.tvContact.text = "${v.contactNumber.ifBlank { "—" }} · ${v.idPresented}"
        b.tvCheckIn.text = fmt.format(Date(v.checkInTime))
        b.tvCheckOut.text = v.checkOutTime?.let { fmt.format(Date(it)) }
            ?: "— not yet checked out —"

        val active = v.status == VisitStatus.ACTIVE
        b.tvStatus.text = if (active) "● ACTIVE" else "● CHECKED OUT"
        b.tvStatus.setTextColor(if (active) 0xFF2E7D32.toInt()
        else 0xFF616161.toInt())
        b.btnCheckOut.isEnabled = active
    }
}