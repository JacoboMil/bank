package com.iobuilders.bank.transaction.adapter.`in`.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.adapter.`in`.rest.TransactionResponseConverter
import com.iobuilders.bank.transaction.domain.usecase.MoneyTransferUseCase
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@RunWith(SpringRunner::class)
@WebMvcTest(TransactionController::class)
internal class TransactionControllerTest : TestUtils() {

    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper
    private lateinit var transactionResponseConverter: TransactionResponseConverter
    private lateinit var transactionController: TransactionController

    @MockBean
    private lateinit var moneyTransferService: MoneyTransferUseCase

    @BeforeEach
    fun setUp() {
        openMocks(this)
        mapper = jacksonObjectMapper()
        transactionResponseConverter = TransactionResponseConverter()
        transactionController = TransactionController(
            moneyTransferService,
            transactionResponseConverter
        )
        mockMvc = standaloneSetup(transactionController).build()
    }

    @Test
    fun whenPostRequestWithCorrectTransaction_thenReturnsStatus201() {
        `when`(moneyTransferService.moneyTransfer(any(), any(), any())).thenReturn(createTransaction())

        mockMvc.perform(
            post("/v1/transactions")
                .content(this.mapper.writeValueAsString(createTransactionRequest()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.amount").value(amount))

    }
}