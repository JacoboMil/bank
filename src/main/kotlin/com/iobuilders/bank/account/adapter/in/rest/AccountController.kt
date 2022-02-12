package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.account.AccountService
import com.iobuilders.bank.api.AccountsApi
import com.iobuilders.bank.model.AccountRequest
import com.iobuilders.bank.model.AccountResponse
import com.iobuilders.bank.model.Accounts
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
class AccountController(
    private val accountService: AccountService,
    private val accountResponseConverter: AccountResponseConverter
): AccountsApi {

    companion object {
        private val log = getLogger(AccountController::class.java)
    }

    override fun createAccount(accountRequest: AccountRequest): ResponseEntity<AccountResponse> {
        log.info("createAccount AccountRequest:$accountRequest")
        return ResponseEntity(
            accountResponseConverter.convert(
                accountService.createAccount(accountRequest.userId)
            ),
            HttpStatus.CREATED
        )
    }

    override fun getAccountById(accountId: UUID): ResponseEntity<AccountResponse> {
        return ResponseEntity(HttpStatus.OK)
    }

    override fun getAccountsByUser(userId: UUID): ResponseEntity<Accounts> {
        return ResponseEntity(HttpStatus.OK)
    }

    override fun updateAccountAmount(accountId: UUID, amount: BigDecimal): ResponseEntity<AccountResponse> {
        return ResponseEntity(HttpStatus.OK)
    }

}