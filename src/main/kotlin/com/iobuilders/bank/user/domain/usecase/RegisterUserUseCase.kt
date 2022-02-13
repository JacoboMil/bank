package com.iobuilders.bank.user.domain.usecase

import com.iobuilders.bank.user.domain.model.User

interface RegisterUserUseCase {

    fun registerUser(
        username: String,
        firstName: String?,
        lastName: String?,
        email: String?,
    ): User
}