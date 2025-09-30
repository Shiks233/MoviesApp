package com.shiksha.moviesapp.repository

class UserRepository {

    fun login(username: String, password: String): Boolean {
        return username == "admin" && password == "password"
    }
}
