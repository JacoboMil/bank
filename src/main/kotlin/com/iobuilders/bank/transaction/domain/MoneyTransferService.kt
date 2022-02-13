package com.iobuilders.bank.transaction.domain

import com.iobuilders.bank.account.domain.AccountRepository
import com.iobuilders.bank.account.domain.exception.AccountNotFoundException
import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.transaction.domain.exception.BalanceNotEnoughException
import com.iobuilders.bank.transaction.domain.model.Operation
import com.iobuilders.bank.transaction.domain.model.Transaction
import com.iobuilders.bank.transaction.domain.usecase.MoneyTransferUseCase
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@Service
@Transactional
class MoneyTransferService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
): MoneyTransferUseCase {

    override fun moneyTransfer(
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
        updateAccount(originAccountId, amount, Operation.SUBSTRACT_AMOUNT)
        updateAccount(destinationAccountId, amount, Operation.ADD_AMOUNT)
    }

    private fun updateAccount(
        accountId: UUID,
        amount: BigDecimal,
        operation: Operation
    ): Account {
        val account: Account = when (operation) {
            Operation.ADD_AMOUNT -> addAmount(accountId, amount)
            Operation.SUBSTRACT_AMOUNT -> substractAmount(accountId, amount)
        }
        return accountRepository.saveAndFlush(account)
    }

    private fun addAmount(accountId: UUID, amount: BigDecimal): Account {
        var account = accountRepository.findByIdOrNull(accountId)  ?: throw AccountNotFoundException(
            "Account with accountId: $accountId not found"
        )
        var newAmount = account.amount.plus(amount)
        account.amount = newAmount
        return account
    }

    private fun substractAmount(accountId: UUID, amount: BigDecimal): Account {
        var account = accountRepository.findByIdOrNull(accountId)  ?: throw AccountNotFoundException(
            "Account with accountId: $accountId not found"
        )
        var newAmount = account.amount.subtract(amount)
        account.amount = newAmount
        return account
    }
}


