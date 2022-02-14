package com.iobuilders.bank.account.adapter.`in`.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.domain.usecase.AccountDepositUseCase
import com.iobuilders.bank.account.domain.usecase.CreateAccountUseCase
import com.iobuilders.bank.account.domain.usecase.DisplayAccountUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.any
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@RunWith(SpringRunner::class)
@WebMvcTest(AccountController::class)
internal class AccountControllerTest : TestUtils() {

    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper
    private lateinit var accountResponseConverter: AccountResponseConverter
    private lateinit var accountBalanceResponseConverter: AccountBalanceResponseConverter
    private lateinit var transactionResponseConverter: TransactionResponseConverter
    private lateinit var transactionsResponseConverter: TransactionsResponseConverter
    private lateinit var accountController: AccountController

    @MockBean
    private lateinit var createAccountService: CreateAccountUseCase
    @MockBean
    private lateinit var displayAccountService: DisplayAccountUseCase
    @MockBean
    private lateinit var accountDepositService: AccountDepositUseCase

    @BeforeEach
    fun setUp() {
        openMocks(this)
        mapper = jacksonObjectMapper()
        accountResponseConverter = AccountResponseConverter()
        accountBalanceResponseConverter = AccountBalanceResponseConverter()
        transactionResponseConverter = TransactionResponseConverter()
        transactionsResponseConverter = TransactionsResponseConverter(transactionResponseConverter)
        accountController = AccountController(
            createAccountService,
            displayAccountService,
            accountDepositService,
            accountResponseConverter,
            accountBalanceResponseConverter,
            transactionsResponseConverter
        )
        mockMvc = standaloneSetup(accountController).build()
    }


    @Test
    fun whenPostRequestWithCorrectUserId_thenReturnsStatus201() {
        `when`(createAccountService.createAccount(any())).thenReturn(createAccount())

        mockMvc.perform(
            post("/v1/accounts")
                .content(this.mapper.writeValueAsString(createAccountRequest()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.iban").value(iban))
    }

    @Test
    fun whenGetAccountBalanceRequestWithValidId_thenReturn200() {
        `when`(displayAccountService.displayAccountBalance(any())).thenReturn(createAccount())

        mockMvc.perform(
            get("/v1/accounts/$uuid/balances")
                .content(this.mapper.writeValueAsString(uuid))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.iban").value(iban))
    }

    @Test
    fun whenGetAccountTransactionsRequestWithValidId_thenReturn200() {
        `when`(displayAccountService.displayAccountTransactions(any())).thenReturn(listOf(createTransaction()))

        mockMvc.perform(
            get("/v1/accounts/$uuid/transactions")
                .content(this.mapper.writeValueAsString(uuid))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.accountId").value(uuid.toString()))
    }

    @Test
    fun whenUpdateAccountRequestWithValidId_thenReturn200() {
        `when`(accountDepositService.accountDeposit(any(), any())).thenReturn(createAccount())

        mockMvc.perform(
            patch("/v1/accounts/$uuid")
                .content(this.mapper.writeValueAsString(createAccountDepositRequest()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.iban").value(iban))
    }
}