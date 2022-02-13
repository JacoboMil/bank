package com.iobuilders.bank.transaction.exception

class TransactionNotFoundException(message: String) : RuntimeException(message) {
    val title = "Transaction Not Found"
}