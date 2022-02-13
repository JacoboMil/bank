package com.iobuilders.bank.account.domain

import com.iobuilders.bank.account.domain.exception.AccountNotFoundException
import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.account.domain.usecase.DisplayAccountUseCase
import com.iobuilders.bank.transaction.domain.TransactionRepository
import com.iobuilders.bank.transaction.domain.model.Transaction
import org.springframework.stereotype.Service

@Service
class DisplayAccountService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
    ): DisplayAccountUseCase {

    override fun displayAccountBalance(iban: String): Account {
        return accountRepository.findByIban(iban) ?: throw AccountNotFoundException("Account with IBAN: $iban not exists")
    }

    override fun displayAccountTransactions(iban: String): List<Transaction> {
        accountRepository.findByIban(iban) ?: throw AccountNotFoundException("Account with IBAN: $iban not exists")

        val originIbanTransactions = transactionRepository.findByOriginAccountIban(iban)
        val destinationIbanTransactions = transactionRepository.findByDestinationAccountIban(iban)

        val allTransactions = mutableListOf<Transaction>()

        allTransactions.addAll(originIbanTransactions)
        allTransactions.addAll(destinationIbanTransactions)

        return allTransactions.sortedBy { it.transactionDate }
    }


}