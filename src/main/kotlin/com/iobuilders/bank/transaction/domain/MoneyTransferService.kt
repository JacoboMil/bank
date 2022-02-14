package com.iobuilders.bank.transaction.domain

import com.iobuilders.bank.account.domain.AccountRepository
import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.account.domain.problem.AccountNotFoundProblem
import com.iobuilders.bank.transaction.domain.problem.BalanceNotEnoughProblem
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
        destinationAccountId: UUID,
        originAccountId: UUID
    ): Transaction {

        val originAccount = retrieveAccount(originAccountId)
        val destinationAccount = retrieveAccount(destinationAccountId)

        if (isEnoughBalance(amount, originAccount.amount)) throw BalanceNotEnoughProblem(originAccount.id)

        balanceTransfer(originAccount.id, destinationAccount.id, amount)

        return transactionRepository.save(
            Transaction(
                id = UUID.randomUUID(),
                amount = amount,
                destinationAccountIban = destinationAccount.iban,
                originAccountIban = originAccount.iban,
                transactionDate = OffsetDateTime.now()
            )
        )
    }

    private fun retrieveAccount(accountId: UUID): Account {
        return accountRepository.findByIdOrNull(accountId)?: throw AccountNotFoundProblem(accountId)
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
        var account = accountRepository.findByIdOrNull(accountId)  ?: throw AccountNotFoundProblem(accountId)
        var newAmount = account.amount.plus(amount)
        account.amount = newAmount
        return account
    }

    private fun substractAmount(accountId: UUID, amount: BigDecimal): Account {
        var account = accountRepository.findByIdOrNull(accountId)  ?: throw AccountNotFoundProblem(accountId)
        var newAmount = account.amount.subtract(amount)
        account.amount = newAmount
        return account
    }
}


