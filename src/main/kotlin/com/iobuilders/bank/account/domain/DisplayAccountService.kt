package com.iobuilders.bank.account.domain

import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.account.domain.problem.AccountNotFoundProblem
import com.iobuilders.bank.account.domain.usecase.DisplayAccountUseCase
import com.iobuilders.bank.transaction.domain.TransactionRepository
import com.iobuilders.bank.transaction.domain.model.Transaction
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class DisplayAccountService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
    ): DisplayAccountUseCase {

    override fun displayAccountBalance(accountId: UUID): Account {
        return accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundProblem(accountId)
    }

    override fun displayAccountTransactions(accountId: UUID): List<Transaction> {
        val account = accountRepository.findByIdOrNull(accountId) ?: throw AccountNotFoundProblem(accountId)

        val originIbanTransactions = transactionRepository.findByOriginAccountIban(account.iban)
        val destinationIbanTransactions = transactionRepository.findByDestinationAccountIban(account.iban)

        val allTransactions = mutableListOf<Transaction>()

        allTransactions.addAll(originIbanTransactions)
        allTransactions.addAll(destinationIbanTransactions)

        return allTransactions.sortedBy { it.transactionDate }
    }


}