package com.example.dormlog.data

import com.example.dormlog.model.User
import com.example.dormlog.model.UserRole

object AuthRepository {

    var currentUser: User? = null

    fun login(email: String, password: String): User? {
        val cleanEmail = email.trim().lowercase()

        if (password != "123456") return null

        return when {
            cleanEmail == "admin@dormlog.com" -> {
                User(cleanEmail, UserRole.ADMIN).also { currentUser = it }
            }
            cleanEmail.endsWith("@dormlog.com") -> {
                User(cleanEmail, UserRole.STAFF).also { currentUser = it }
            }
            cleanEmail.endsWith("@gmail.com") -> {
                User(cleanEmail, UserRole.RESIDENT, residentName = "Maria Santos").also { currentUser = it }
            }
            else -> null
        }
    }

    fun logout() {
        currentUser = null
    }
}