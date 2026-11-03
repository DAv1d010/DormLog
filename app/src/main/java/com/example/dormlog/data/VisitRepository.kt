package com.example.dormlog.data

import com.example.dormlog.model.Visit
import com.example.dormlog.model.VisitStatus
import java.util.UUID

object VisitRepository {
    private val visits = mutableListOf<Visit>()

    fun getActive(): List<Visit> {
        return visits.filter { it.status == VisitStatus.ACTIVE }
    }

    fun getHistory(): List<Visit> {
        return visits.filter { it.status == VisitStatus.CHECKED_OUT }
    }

    fun findById(id: String): Visit? {
        return visits.find { it.visitId == id }
    }

    fun createVisit(
        visitorName: String,
        contactNumber: String,
        idPresented: String,
        residentName: String,
        roomNumber: String,
        purpose: String,
        durationMinutes: Int
    ): String? {
        val existingActive = visits.any {
            it.status == VisitStatus.ACTIVE &&
                    it.visitorName.equals(visitorName, ignoreCase = true) &&
                    it.residentName.equals(residentName, ignoreCase = true)
        }
        if (existingActive) return null

        val id = UUID.randomUUID().toString()
        val visit = Visit(
            visitId = id,
            visitorName = visitorName,
            contactNumber = contactNumber,
            idPresented = idPresented,
            residentName = residentName,
            roomNumber = roomNumber,
            purpose = purpose,
            durationMinutes = durationMinutes,
            checkInTime = System.currentTimeMillis()
        )
        visits.add(0, visit)
        return id
    }

    fun checkOut(id: String): Boolean {
        val index = visits.indexOfFirst { it.visitId == id }
        if (index == -1) return false
        val v = visits[index]
        if (v.status != VisitStatus.ACTIVE) return false
        visits[index] = v.copy(
            status = VisitStatus.CHECKED_OUT,
            checkOutTime = System.currentTimeMillis()
        )
        return true
    }

    fun delete(id: String): Boolean {
        return visits.removeIf { it.visitId == id }
    }
}
