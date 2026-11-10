package com.example.dormlog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityNewVisitBinding

class NewVisitActivity : AppCompatActivity() {

    private lateinit var b: ActivityNewVisitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityNewVisitBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnSave.setOnClickListener { save() }
    }

    private fun save() {
        listOf(b.tilVisitor, b.tilId, b.tilResident, b.tilPurpose).forEach {
            it.error = null
        }

        val visitor = b.etVisitor.text.toString().trim()
        val contact = b.etContact.text.toString().trim()
        val id = b.etId.text.toString().trim()
        val resident = b.etResident.text.toString().trim()
        val room = b.etRoom.text.toString().trim()
        val purpose = b.etPurpose.text.toString().trim()
        val durStr = b.etDuration.text.toString().trim()

        var valid = true

        if (visitor.isEmpty()) { b.tilVisitor.error = "Required"; valid = false }
        if (id.isEmpty()) { b.tilId.error = "Required"; valid = false }
        if (resident.isEmpty()) { b.tilResident.error = "Required"; valid = false }
        if (purpose.isEmpty()) { b.tilPurpose.error = "Required"; valid = false }

        if (contact.isNotEmpty() &&
            !Regex("^\\d{7,15}$").matches(contact)) {
            b.tilContact.error = "7–15 digits only"; valid = false
        }

        val duration = durStr.toIntOrNull() ?: 60
        if (duration < 15 || duration > 720) {
            b.tilDuration.error = "15–720 minutes"; valid = false
        }

        if (!valid) return

        val newId = VisitRepository.createVisit(
            visitor, contact, id, resident, room, purpose, duration
        )

        if (newId == null) {
            Toast.makeText(this,
                "This visitor already has an active visit to $resident.",
                Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(this, "Checked in successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}