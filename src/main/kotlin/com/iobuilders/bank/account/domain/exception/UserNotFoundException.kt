package com.iobuilders.bank.account.domain.exception

class UserNotFoundException(message: String) : RuntimeException(message) {
    val title = "User Not Found"
}
