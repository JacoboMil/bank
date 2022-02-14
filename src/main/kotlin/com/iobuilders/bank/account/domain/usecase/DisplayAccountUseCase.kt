package com.iobuilders.bank.account.domain.usecase

import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.transaction.domain.model.Transaction
import java.util.*

interface DisplayAccountUseCase {

    fun displayAccountBalance(accountId: UUID): Account
    fun displayAccountTransactions(accountId: UUID): List<Transaction>

}