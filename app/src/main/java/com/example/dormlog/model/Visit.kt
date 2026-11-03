package com.example.dormlog.model

data class Visit(
    val visitId: String,
    val visitorName: String,
    val contactNumber: String,
    val idPresented: String,
    val residentName: String,
    val roomNumber: String,
    val purpose: String,
    val durationMinutes: Int,
    val checkInTime: Long,
    val checkOutTime: Long? = null,
    val status: VisitStatus = VisitStatus.ACTIVE
)
