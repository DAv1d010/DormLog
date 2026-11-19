package com.example.dormlog

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dormlog.data.AuthRepository
import com.example.dormlog.data.VisitRepository
import com.example.dormlog.databinding.ActivityNewVisitBinding
import com.example.dormlog.model.UserRole

class NewVisitActivity : AppCompatActivity() {

    private lateinit var b: ActivityNewVisitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityNewVisitBinding.inflate(layoutInflater)
        setContentView(b.root)

        val currentUser = AuthRepository.currentUser
        val residents = AuthRepository.getRegisteredResidents(this)
        val residentNames = residents.map { t -> t.first }

        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, residentNames)
        b.etResident.setAdapter(adapter)

        b.etResident.setOnItemClickListener { _, _, position, _ ->
            val selectedName = residentNames[position]
            val matchedRoom = residents.find { it.first.equals(selectedName, true) }?.second ?: ""
            b.etRoom.setText(matchedRoom)
        }

        if (currentUser?.role == UserRole.RESIDENT) {
            b.etResident.setText(currentUser.residentName)
            b.etResident.isEnabled = false
            b.etRoom.setText(currentUser.roomNumber)
            b.etRoom.isEnabled = false
        }

        b.btnSave.setOnClickListener { save() }
    }

    private fun save() {
        listOf(b.tilVisitor, b.tilId, b.tilResident, b.tilRoom, b.tilPurpose).forEach {
            it.error = null
        }

        val visitor = b.etVisitor.text.toString().trim()
        val contact = b.etContact.text.toString().trim()
        val id = b.etId.text.toString().trim()
        val currentUser = AuthRepository.currentUser
        val resident = if (currentUser?.role == UserRole.RESIDENT) {
            currentUser.residentName
        } else {
            b.etResident.text.toString().trim()
        }
        val room = b.etRoom.text.toString().trim()
        val purpose = b.etPurpose.text.toString().trim()
        val durStr = b.etDuration.text.toString().trim()

        var valid = true

        if (visitor.isEmpty()) { b.tilVisitor.error = "Required"; valid = false }
        if (id.isEmpty()) { b.tilId.error = "Required"; valid = false }
        if (resident.isEmpty()) { b.tilResident.error = "Required"; valid = false }
        if (room.isEmpty()) { b.tilRoom.error = "Required"; valid = false }
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
            visitor, contact, id, resident, room, purpose, duration,
        )

        if (newId == null) {
            Toast.makeText(this,
                "This visitor already has an active visit to $resident.",
                Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(this, "Visit created successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}