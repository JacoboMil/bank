package com.iobuilders.bank

import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.model.*
import com.iobuilders.bank.transaction.domain.model.Transaction
import com.iobuilders.bank.user.domain.model.User
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

open class TestUtils {

    var uuid: UUID = UUID.fromString("f06e2a04-3712-4f6e-9301-99f6f0b708ce")
    var iban = "ES3704747712313420831048"
    var text = "test"
    var email = "test@test.com"
    var amount: BigDecimal = BigDecimal.valueOf(1400.85)
    var amountTransfer: BigDecimal = BigDecimal.valueOf(140.85)



    fun createUser(): User {
        return User(
            id = uuid,
            username = text,
            firstName = text,
            lastName = text,
            email = email
        )
    }

    fun createUserRequest(): RegisterUserRequest {
        return RegisterUserRequest(
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

    fun createAccountDepositRequest(): AccountDepositRequest {
        return AccountDepositRequest(
            amount = amount,
        )
    }

    fun createTransaction(): Transaction {
        return Transaction(
            id = uuid,
            amount = amount,
            destinationAccountIban = iban,
            originAccountIban = iban,
            transactionDate = OffsetDateTime.now()
        )
    }

    fun createTransactionRequest(): TransactionRequest {
        return TransactionRequest(
            amount = amount,
            destinationAccountId = uuid,
            originAccountId = uuid
        )
    }

}