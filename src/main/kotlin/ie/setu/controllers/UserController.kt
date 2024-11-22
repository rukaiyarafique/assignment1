package ie.setu.controllers

import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

class UserController {
    private val userDao = UserDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }
}