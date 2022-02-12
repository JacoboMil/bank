package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.api.UsersApi
import com.iobuilders.bank.model.UserRequest
import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.UserService
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*


@RestController
class UserController(
    private val userService: UserService,
    private val userResponseConverter: UserResponseConverter
    ): UsersApi {

    companion object {
        private val log = getLogger(UserController::class.java)
    }

    override fun createUser(userRequest: UserRequest): ResponseEntity<UserResponse> {
        log.info("createUser UserRequest:$userRequest")

        val user = userService.createUser(
            userRequest.username,
            userRequest.firstName,
            userRequest.lastName,
            userRequest.email
        )

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.id)
            .toUri()

        return ResponseEntity.created(uri).body(userResponseConverter.convert(user))
    }

    override fun getUserById(userId: UUID): ResponseEntity<UserResponse> {
        log.info("getUserById userId:$userId")
        return ResponseEntity.ok(
            userResponseConverter.convert(userService.getUserById(userId))
        )
    }

    override fun deleteUser(userId: UUID): ResponseEntity<Unit> {
        log.info("deleteUserById userId:$userId")
        userService.deleteUser(userId)
        return ResponseEntity.noContent().build()
    }

}