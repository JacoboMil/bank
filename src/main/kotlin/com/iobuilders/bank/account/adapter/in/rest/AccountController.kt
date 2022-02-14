package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.account.domain.usecase.AccountDepositUseCase
import com.iobuilders.bank.account.domain.usecase.CreateAccountUseCase
import com.iobuilders.bank.account.domain.usecase.DisplayAccountUseCase
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
    private val createAccountService: CreateAccountUseCase,
    private val displayAccountService: DisplayAccountUseCase,
    private val accountDepositService: AccountDepositUseCase,
    private val accountResponseConverter: AccountResponseConverter,
    private val accountBalanceResponseConverter: AccountBalanceResponseConverter,
    private val transactionsResponseConverter: TransactionsResponseConverter
): AccountsApi {

    companion object {
        private val log = getLogger(AccountController::class.java)
    }

    override fun createAccount(createAccountRequest: CreateAccountRequest): ResponseEntity<AccountResponse> {
        log.info("createAccount AccountRequest:$createAccountRequest")
        val userId = createAccountRequest.userId
        val account = createAccountService.createAccount(userId)

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userId)
            .toUri()

        return ResponseEntity.created(uri).body(accountResponseConverter.convert(account))
    }

    override fun getAccountBalance(accountId: UUID): ResponseEntity<AccountBalanceResponse> {
        log.info("getAccountBalance accountId:$accountId")
        return ResponseEntity.ok(
            accountBalanceResponseConverter.convert(
                displayAccountService.displayAccountBalance(accountId)
            )
        )
    }

    override fun getAccountTransactions(accountId: UUID): ResponseEntity<AccountTransactionsResponse> {
        log.info("getAccountTransactions accountId:$accountId")

        val transactions = displayAccountService.displayAccountTransactions(accountId)

        return ResponseEntity.ok(
            AccountTransactionsResponse(
                accountId = accountId,
                transactions = transactionsResponseConverter.convert(transactions)
            )
        )
    }

    override fun updateAccountBalance(
        accountId: UUID,
        accountDepositRequest: AccountDepositRequest
    ): ResponseEntity<AccountResponse> {
        log.info("updateAccountBalance accountId:$accountId with $accountDepositRequest")
        return ResponseEntity.ok(
            accountResponseConverter.convert(
                accountDepositService.accountDeposit(accountId, accountDepositRequest.amount)
            )
        )
    }
}