package com.iobuilders.bank.account.domain.exception

class AccountNotFoundException(message: String) : RuntimeException(message) {
    val title = "Account Not Found"
}
