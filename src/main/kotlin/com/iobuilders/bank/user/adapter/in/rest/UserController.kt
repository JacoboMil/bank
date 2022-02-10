package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.api.UsersApi
import com.iobuilders.bank.model.UserRequest
import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UserController(
    private val userService: UserService
    ): UsersApi {

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
    }

    override fun createUser(userRequest: UserRequest): ResponseEntity<UserResponse> {
        return super.createUser(userRequest)
    }

    override fun getUserById(userID: UUID): ResponseEntity<UserResponse> {
        return super.getUserById(userID)
    }


}