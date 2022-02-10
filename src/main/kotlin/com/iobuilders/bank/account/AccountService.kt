package com.iobuilders.bank.account

import com.iobuilders.bank.account.model.Account
import com.iobuilders.bank.user.UserService
import com.iobuilders.bank.user.exceptions.UserNotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountService(
    private val userService: UserService,
    private val accountRepository: AccountRepository
    ) {

    fun createAccount(account: Account): Account {
        //userService.getUserById(account.userId) ?: throw UserNotFoundException("The account was not created because user: ${account.userId} was not found.")
        return accountRepository.save(account)
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
