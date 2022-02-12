package com.iobuilders.bank.account

import com.iobuilders.bank.account.exceptions.AccountNotFoundException
import com.iobuilders.bank.account.model.Account
import com.iobuilders.bank.model.UpdateAccountRequest
import com.iobuilders.bank.user.UserRepository
import com.iobuilders.bank.user.exceptions.UserNotFoundException
import org.iban4j.CountryCode
import org.iban4j.Iban
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
    ) {

    fun createAccount(userId: UUID): Account {
        userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException("The account was not created because user: $userId was not found.")
        return accountRepository.save(
            Account(
                id = UUID.randomUUID(),
                iban = Iban.random(CountryCode.ES).toString(),
                userId = userId,
                amount = BigDecimal.ZERO
            )
        )
    }

    fun getAccount(accountId: UUID): Account {
        return accountRepository.findByIdOrNull(accountId)  ?: throw AccountNotFoundException("Account with accountId: $accountId not found")
    }

    fun getAccountsByUser(userId: UUID): List<Account> {
        return accountRepository.findByUserId(userId)
    }

    fun getAccountByIBAN(iban: String): Account {
        return accountRepository.findByIban(iban) ?: throw AccountNotFoundException("Account with IBAN: $iban not found")
    }

    fun updateAccount(
        accountId: UUID,
        amount: BigDecimal,
        operation: UpdateAccountRequest.Operation
    ): Account {
        val account: Account = when (operation) {
            UpdateAccountRequest.Operation.add -> addAmount(accountId, amount)
            UpdateAccountRequest.Operation.substract -> substractAmount(accountId, amount)
        }
        return accountRepository.saveAndFlush(account)
    }

    private fun addAmount(accountId: UUID, amount: BigDecimal): Account {
        var account = accountRepository.getById(accountId)
        var newAmount = account.amount.plus(amount)
        account.amount = newAmount
        return account
    }

    private fun substractAmount(accountId: UUID, amount: BigDecimal): Account {
        var account = accountRepository.getById(accountId)
        var newAmount = account.amount.subtract(amount)
        account.amount = newAmount
        return account
    }
}
