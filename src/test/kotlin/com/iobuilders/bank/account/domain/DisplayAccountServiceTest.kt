package com.iobuilders.bank.account.domain

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.domain.problem.AccountNotFoundProblem
import com.iobuilders.bank.transaction.domain.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.*

@RunWith(MockitoJUnitRunner::class)
internal class DisplayAccountServiceTest: TestUtils() {

    private lateinit var displayAccountService: DisplayAccountService

    @Mock
    lateinit var accountRepository: AccountRepository

    @Mock
    lateinit var transactionRepository: TransactionRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        displayAccountService = DisplayAccountService(accountRepository, transactionRepository)
    }

    @Test
    fun displayAccountBalanceTest() {
        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))

        val result = displayAccountService.displayAccountBalance(uuid)

        verify(accountRepository, times(1)).findById(uuid)
        assertNotNull(result)
        assertEquals(result.id, uuid)
    }

    @Test
    fun displayAccountTransactions() {
        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(transactionRepository.findByOriginAccountIban(any())).thenReturn(listOf(createTransaction()))
        `when`(transactionRepository.findByDestinationAccountIban(any())).thenReturn(listOf(createTransaction()))

        val result = displayAccountService.displayAccountTransactions(uuid)

        verify(accountRepository, times(1)).findById(uuid)
        verify(transactionRepository, times(1)).findByOriginAccountIban(iban)
        verify(transactionRepository, times(1)).findByDestinationAccountIban(iban)
        assertNotNull(result)
        assertEquals(result.count(), 2)
    }

    @Test
    fun accountNotFoundWhenBalancesRequestTest() {
        val exception = org.junit.jupiter.api.assertThrows<AccountNotFoundProblem> {
            displayAccountService.displayAccountBalance(uuid)
        }
        assertEquals("Not found: Account $uuid not found.", exception.message)
    }

    @Test
    fun accountNotFoundWhenTransactionsRequestTest() {
        val exception = org.junit.jupiter.api.assertThrows<AccountNotFoundProblem> {
            displayAccountService.displayAccountTransactions(uuid)
        }
        assertEquals("Not found: Account $uuid not found.", exception.message)
    }

}