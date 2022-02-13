package com.iobuilders.bank.account.adapter.`in`.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.AccountService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringRunner::class)
@WebMvcTest(AccountController::class)
internal class AccountControllerTest : TestUtils() {

    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper
    private lateinit var accountResponseConverter: AccountResponseConverter
    private lateinit var accountsResponseConverter: AccountsResponseConverter
    private lateinit var accountController: AccountController
    @MockBean
    private lateinit var accountService: AccountService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mapper = jacksonObjectMapper()
        accountResponseConverter = AccountResponseConverter()
        accountsResponseConverter = AccountsResponseConverter(accountResponseConverter)
        accountController = AccountController(accountService, accountResponseConverter, accountsResponseConverter)
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build()
    }


    @Test
    fun whenPostRequestWithCorrectUserId_thenReturnsStatus201() {
        `when`(accountService.createAccount(any())).thenReturn(createAccount())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/accounts")
            .content(this.mapper.writeValueAsString(createAccountRequest()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value(iban))
    }

    @Test
    fun whenGetAccountRequestWithValidId_thenReturn200() {
        `when`(accountService.getAccount(uuid)).thenReturn(createAccount())

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/accounts/$uuid")
            .content(this.mapper.writeValueAsString(uuid))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value(iban))
    }

    @Test
    fun whenGetAccountsByUserRequestIdWithValidId_thenReturn200() {
        `when`(accountService.getAccountsByUser(uuid)).thenReturn(listOf( createAccount()))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/accounts?userId=$uuid")
                .content(this.mapper.writeValueAsString(uuid))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            //.andExpect(MockMvcResultMatchers.jsonPath("$.iban").value(iban))
    }

    @Test
    fun whenGetAccountByIbanRequestWithValidIban_thenReturn200() {
        `when`(accountService.getAccountByIban(iban)).thenReturn(createAccount())

        mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/accounts/$iban")
                .content(this.mapper.writeValueAsString(iban))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value(iban))
    }

    @Test
    fun whenUpdateAccountRequestWithValidIban_thenReturn200() {
        `when`(accountService.updateAccount(any(), any(), any())).thenReturn(createAccount())

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/v1/accounts/$uuid")
            .content(this.mapper.writeValueAsString(updateAccountRequest()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value(iban))
    }
}