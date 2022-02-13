package com.iobuilders.bank.account.domain.usecase

import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.transaction.domain.model.Transaction

interface DisplayAccountUseCase {

    fun displayAccountBalance(iban: String): Account
    fun displayAccountTransactions(iban: String): List<Transaction>

}