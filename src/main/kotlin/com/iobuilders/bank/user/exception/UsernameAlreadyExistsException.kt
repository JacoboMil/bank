package com.iobuilders.bank.user.exception

class UsernameAlreadyExistsException(message: String) : RuntimeException(message) {
    val title = "Username Already Exists"
}
