package com.example.dormlog.data

import android.content.Context
import com.example.dormlog.model.User
import com.example.dormlog.model.UserRole
import org.json.JSONArray
import org.json.JSONObject

object AuthRepository {

    var currentUser: User? = null

    private const val PREFS_NAME = "dormlog_prefs"
    private const val KEY_USERS = "registered_users"

    fun registerResident(context: Context, email: String, pass: String, name: String, room: String): Boolean {
        val cleanEmail = email.trim().lowercase()
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usersJsonStr = prefs.getString(KEY_USERS, "[]") ?: "[]"
        val jsonArray = JSONArray(usersJsonStr)

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            if (obj.getString("email") == cleanEmail) {
                return false
            }
        }

        val newUserObj = JSONObject().apply {
            put("email", cleanEmail)
            put("password", pass)
            put("name", name)
            put("room", room)
        }
        jsonArray.put(newUserObj)
        prefs.edit().putString(KEY_USERS, jsonArray.toString()).apply()
        return true
    }

    fun getRegisteredResidents(context: Context): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        list.add(Pair("Maria Santos", "302"))

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usersJsonStr = prefs.getString(KEY_USERS, "[]") ?: "[]"
        val jsonArray = JSONArray(usersJsonStr)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val name = obj.getString("name")
            val room = obj.getString("room")
            if (!list.any { it.first.equals(name, true) }) {
                list.add(Pair(name, room))
            }
        }
        return list
    }

    fun login(context: Context, email: String, password: String): User? {
        val cleanEmail = email.trim().lowercase()

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val usersJsonStr = prefs.getString(KEY_USERS, "[]") ?: "[]"
        val jsonArray = JSONArray(usersJsonStr)

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            if (obj.getString("email") == cleanEmail) {
                if (obj.getString("password") == password) {
                    return User(
                        email = cleanEmail,
                        role = UserRole.RESIDENT,
                        residentName = obj.getString("name"),
                        roomNumber = obj.getString("room")
                    ).also { currentUser = it }
                } else {
                    return null
                }
            }
        }

        if (password != "123456") return null

        return when {
            cleanEmail == "admin@dormlog.com" -> {
                User(cleanEmail, UserRole.ADMIN).also { currentUser = it }
            }
            cleanEmail.endsWith("@dormlog.com") -> {
                User(cleanEmail, UserRole.STAFF).also { currentUser = it }
            }
            cleanEmail.endsWith("@gmail.com") -> {
                User(cleanEmail, UserRole.RESIDENT, residentName = "Maria Santos", roomNumber = "302").also { currentUser = it }
            }
            else -> null
        }
    }

    fun logout() {
        currentUser = null
    }
}