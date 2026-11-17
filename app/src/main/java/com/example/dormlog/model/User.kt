package com.example.dormlog.model

enum class UserRole { RESIDENT, STAFF, ADMIN }

data class User(
    val email: String,
    val role: UserRole,
    val residentName: String = ""
)