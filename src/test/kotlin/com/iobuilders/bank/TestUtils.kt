package com.iobuilders.bank

import com.iobuilders.bank.model.UserRequest
import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.model.User
import java.util.*

open class TestUtils {

    var uuid: UUID = UUID.fromString("f06e2a04-3712-4f6e-9301-99f6f0b708ce")
    var text = "test"
    var email = "test@test.com"


    fun createUser(): User {
        return User(
            id = uuid,
            username = text,
            firstName = text,
            lastName = text,
            email = email
        )
    }

    fun createUserRequest(): UserRequest {
        return UserRequest(
            username = text,
            firstName = text,
            lastName = text,
            email = email
        )
    }

    fun createUserResponse(): UserResponse {
        return UserResponse(
            userId = uuid,
            username = text,
            firstName = text,
            lastName = text,
            email = email
        )
    }

}