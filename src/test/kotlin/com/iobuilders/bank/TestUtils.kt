package com.iobuilders.bank

import com.iobuilders.bank.account.model.Account
import com.iobuilders.bank.model.CreateAccountRequest
import com.iobuilders.bank.model.UpdateAccountRequest
import com.iobuilders.bank.model.UserRequest
import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.model.User
import java.math.BigDecimal
import java.util.*

open class TestUtils {

    var uuid: UUID = UUID.fromString("f06e2a04-3712-4f6e-9301-99f6f0b708ce")
    var iban = "ES3704747712313420831048"
    var text = "test"
    var email = "test@test.com"
    var amount = BigDecimal.valueOf(1400.85)


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

    fun createAccount(): Account {
        return Account(
            id = uuid,
            iban = iban,
            userId = uuid,
            amount = amount
        )
    }

    fun createAccountRequest(): CreateAccountRequest {
        return CreateAccountRequest(
            userId = uuid
        )
    }

    fun updateAccountRequest(): UpdateAccountRequest {
        return UpdateAccountRequest(
            amount = amount,
            operation = UpdateAccountRequest.Operation.add
        )
    }

}