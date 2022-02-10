package com.iobuilders.bank.user.exceptions

class UsernameAlreadyExistsException(message: String) : RuntimeException(message) {
    val title = "Username Already Exists"
}
