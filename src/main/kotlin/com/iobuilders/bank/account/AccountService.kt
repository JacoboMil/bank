package com.iobuilders.bank.account

import com.iobuilders.bank.account.model.Account
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
        return accountRepository.getById(accountId)
    }

    fun getAccountsByUser(userId: UUID): List<Account> {
        return accountRepository.findByUserId(userId)
    }

    /*
    fun getAccountByIBAN(iban: String): Account {
        return accountRepository.findByIBAN(iban)
    }

     */

    fun substractAmount(accountId: UUID, amount: BigDecimal): Account {
        val account = accountRepository.getById(accountId)
        account.amount.subtract(amount)
        return account
    }

    fun addAmount(accountId: UUID, amount: BigDecimal): Account {
        val account = accountRepository.getById(accountId)
        account.amount.add(amount)
        return account
    }

}
