package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.api.UsersApi
import com.iobuilders.bank.model.RegisterUserRequest
import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.domain.usecase.RegisterUserUseCase
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import java.net.URI

@RestController
class UserController(
    private val registerUserService: RegisterUserUseCase,
    private val userResponseConverter: UserResponseConverter
    ): UsersApi {

    companion object {
        private val log = getLogger(UserController::class.java)
    }

    override fun registerUser(registerUserRequest: RegisterUserRequest): ResponseEntity<UserResponse> {
        log.info("registerUser RegisterUserRequest:$registerUserRequest")

        val user = registerUserService.registerUser(
            registerUserRequest.username,
            registerUserRequest.firstName,
            registerUserRequest.lastName,
            registerUserRequest.email
        )

        val uri: URI = fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.id)
            .toUri()

        return ResponseEntity.created(uri).body(userResponseConverter.convert(user))
    }
}