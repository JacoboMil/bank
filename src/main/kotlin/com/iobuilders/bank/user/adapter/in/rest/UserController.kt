package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.api.UsersApi
import com.iobuilders.bank.model.UserRequest
import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
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

        log.info("createUser UserRequest:$userRequest")

        val newUser = userService.createUser(
            userRequest.username,
            userRequest.firstName,
            userRequest.lastName,
            userRequest.email
        )

        return ResponseEntity(
            UserResponse(
                userId = newUser.id,
                firstName = newUser.firstName,
                lastName = newUser.lastName,
                email = newUser.email
            ),
            HttpStatus.CREATED
        )
    }

    override fun getUserById(userId: UUID): ResponseEntity<UserResponse> {

        log.info("getUserById userId:$userId")

        val user = userService.getUserById(userId)

        return ResponseEntity(
            UserResponse(
                userId = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email
            ),
            HttpStatus.OK
        )
    }

    override fun deleteUser(userId: UUID): ResponseEntity<UserResponse> {
        log.info("deleteUserById userId:$userId")

        userService.deleteUser(userId)
        return ResponseEntity(HttpStatus.OK)
    }

}