package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.account.AccountService
import com.iobuilders.bank.api.AccountsApi
import com.iobuilders.bank.model.*
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
class AccountController(
    private val accountService: AccountService,
    private val accountResponseConverter: AccountResponseConverter,
    private val accountsResponseConverter: AccountsResponseConverter
): AccountsApi {

    companion object {
        private val log = getLogger(AccountController::class.java)
    }

    override fun createAccount(createAccountRequest: CreateAccountRequest): ResponseEntity<AccountResponse> {
        log.info("createAccount AccountRequest:$createAccountRequest")
        val userId = createAccountRequest.userId
        val account = accountService.createAccount(userId)

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userId)
            .toUri()

        return ResponseEntity.created(uri).body(accountResponseConverter.convert(account))
    }

    override fun getAccountById(accountId: UUID): ResponseEntity<AccountResponse> {
        log.info("getAccountById accountId:$accountId")
        return ResponseEntity.ok(
            accountResponseConverter.convert(
                accountService.getAccount(accountId)
            )
        )
    }

    override fun getAccounts(userId: UUID?): ResponseEntity<AccountsResponse> {
        log.info("getAccounts")
        return ResponseEntity.ok(
            accountsResponseConverter.convert(
                accountService.getAccounts(userId)
            )
        )
    }

    override fun updateAccount(accountId: UUID, updateAccountRequest: UpdateAccountRequest): ResponseEntity<AccountResponse> {
        log.info("updateAccount accountId:$accountId with $updateAccountRequest")
        return ResponseEntity.ok(
            accountResponseConverter.convert(
                accountService.updateAccount(accountId, updateAccountRequest.amount, updateAccountRequest.operation)
            )
        )
    }

}