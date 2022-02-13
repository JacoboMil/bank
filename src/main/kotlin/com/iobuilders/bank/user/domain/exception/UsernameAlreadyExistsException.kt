package com.iobuilders.bank.user.domain.exception

class UsernameAlreadyExistsException(message: String) : RuntimeException(message) {
    val title = "Username Already Exists"
}
