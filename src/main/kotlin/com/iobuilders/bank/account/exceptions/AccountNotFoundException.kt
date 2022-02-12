package com.iobuilders.bank.account.exceptions

class AccountNotFoundException(message: String) : RuntimeException(message) {
    val title = "Account Not Found"
}
