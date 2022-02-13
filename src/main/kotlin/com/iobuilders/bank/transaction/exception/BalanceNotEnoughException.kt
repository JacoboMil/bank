package com.iobuilders.bank.transaction.exception

class BalanceNotEnoughException(message: String) : RuntimeException(message) {
    val title = "Account with insufficient balance"
}