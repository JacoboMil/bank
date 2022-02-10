package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.account.AccountService
import com.iobuilders.bank.api.AccountsApi
import com.iobuilders.bank.model.AccountRequest
import com.iobuilders.bank.model.AccountResponse
import com.iobuilders.bank.model.Accounts
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
class AccountController(
    private val accountService: AccountService
    ): AccountsApi {

    companion object {
        private val log = LoggerFactory.getLogger(AccountController::class.java)
    }

    override fun createAccount(accountRequest: AccountRequest): ResponseEntity<AccountResponse> {
        return super.createAccount(accountRequest)
    }

    override fun getAccountById(accountID: UUID): ResponseEntity<AccountResponse> {
        return super.getAccountById(accountID)
    }

    override fun getAccountsByUser(userID: UUID): ResponseEntity<Accounts> {
        return super.getAccountsByUser(userID)
    }

    override fun updateAccountAmount(accountID: UUID, amount: BigDecimal): ResponseEntity<AccountResponse> {
        return super.updateAccountAmount(accountID, amount)
    }

}