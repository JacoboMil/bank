package com.iobuilders.bank.user.exceptions

class UserNotFoundException(message: String) : RuntimeException(message) {
    val title = "User Not Found"
}
