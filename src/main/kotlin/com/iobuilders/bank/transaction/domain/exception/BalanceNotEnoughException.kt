package com.iobuilders.bank.transaction.domain.exception

class BalanceNotEnoughException(message: String) : RuntimeException(message) {
    val title = "Account with insufficient balance"
}