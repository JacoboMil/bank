package com.iobuilders.bank.user.exception

class UserNotFoundException(message: String) : RuntimeException(message) {
    val title = "User Not Found"
}
