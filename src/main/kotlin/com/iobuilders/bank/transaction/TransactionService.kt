package com.iobuilders.bank.transaction

import com.iobuilders.bank.account.AccountRepository
import com.iobuilders.bank.account.AccountService
import com.iobuilders.bank.account.model.Account
import com.iobuilders.bank.model.UpdateAccountRequest
import com.iobuilders.bank.transaction.exception.BalanceNotEnoughException
import com.iobuilders.bank.transaction.exception.TransactionNotFoundException
import com.iobuilders.bank.transaction.model.Transaction
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*
import javax.security.auth.login.AccountNotFoundException

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val accountService: AccountService
) {

    @Transactional
    fun createTransaction(
        amount: BigDecimal,
        destinationAccountIban: String,
        originAccountIban: String
    ): Transaction {

        val originAccount = retrieveAccount(originAccountIban)
        val destinationAccount = retrieveAccount(destinationAccountIban)

        if (isEnoughBalance(amount, originAccount.amount)) throw BalanceNotEnoughException("Account: ${originAccount.iban} does not have enough balance.")

        balanceTransfer(originAccount.id, destinationAccount.id, amount)

        return transactionRepository.save(
            Transaction(
                id = UUID.randomUUID(),
                amount = amount,
                destinationAccountIban = destinationAccountIban,
                originAccountIban = originAccountIban,
                transactionDate = OffsetDateTime.now()
            )
        )
    }

    fun getTransactionById(transactionId: UUID): Transaction {
        return transactionRepository.findByIdOrNull(transactionId) ?: throw TransactionNotFoundException("Transaction with id: $transactionId was not found")
    }

    fun getTransactions(originAccountIban: String?, destinationAccountIban: String?): List<Transaction> {
        var transactions = transactionRepository.findAll()
        originAccountIban?.let {
            accountRepository.findByIban(it) ?: throw AccountNotFoundException("Account with IBAN:$originAccountIban was not found.")
            transactions = transactions.filter { transaction -> transaction.originAccountIban == it }
        }
        destinationAccountIban?.let {
            accountRepository.findByIban(it) ?: throw AccountNotFoundException("Account with IBAN:$destinationAccountIban was not found.")
            transactions = transactions.filter { transaction -> transaction.originAccountIban == it }
        }
        return transactions
    }

    private fun retrieveAccount(iban: String): Account {
        return accountRepository.findByIban(iban)?: throw AccountNotFoundException("Account with IBAN: $iban not exists")
    }

    private fun isEnoughBalance(amount: BigDecimal, accountAmount: BigDecimal): Boolean {
        return when (amount.compareTo(accountAmount)) {
            0 -> true
            1 -> true
            else -> false
        }
    }

    private fun balanceTransfer(originAccountId: UUID, destinationAccountId: UUID, amount: BigDecimal) {
        accountService.updateAccount(originAccountId, amount, UpdateAccountRequest.Operation.substract)
        accountService.updateAccount(destinationAccountId, amount, UpdateAccountRequest.Operation.add)
    }

}
