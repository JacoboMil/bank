package com.iobuilders.bank.account.exception

class AccountNotFoundException(message: String) : RuntimeException(message) {
    val title = "Account Not Found"
}
