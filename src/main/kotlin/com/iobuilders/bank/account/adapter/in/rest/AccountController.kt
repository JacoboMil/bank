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

    override fun getAccountById(accountId: UUID): ResponseEntity<AccountResponse> {
        return super.getAccountById(accountId)
    }

    override fun getAccountsByUser(userId: UUID): ResponseEntity<Accounts> {
        return super.getAccountsByUser(userId)
    }

    override fun updateAccountAmount(accountId: UUID, amount: BigDecimal): ResponseEntity<AccountResponse> {
        return super.updateAccountAmount(accountId, amount)
    }

}