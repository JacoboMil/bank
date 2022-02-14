package com.iobuilders.bank.transaction.domain

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.domain.AccountRepository
import com.iobuilders.bank.account.domain.problem.AccountNotFoundProblem
import com.iobuilders.bank.transaction.domain.problem.BalanceNotEnoughProblem
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
internal class MoneyTransferServiceTest: TestUtils() {

    private lateinit var moneyTransferService: MoneyTransferService

    @Mock
    lateinit var transactionRepository: TransactionRepository

    @Mock
    lateinit var accountRepository: AccountRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        moneyTransferService = MoneyTransferService(transactionRepository, accountRepository)
    }

    @Test
    fun moneyTransferTest() {
        `when`(accountRepository.findByIban(any())).thenReturn(createAccount())
        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(accountRepository.saveAndFlush(any())).thenReturn(createAccount())
        `when`(transactionRepository.save(any())).thenReturn(createTransaction())

        val result = moneyTransferService.moneyTransfer(amountTransfer, uuid, uuid)

        verify(accountRepository, times(4)).findById(any())
        verify(accountRepository, times(2)).saveAndFlush(any())
        verify(transactionRepository, times(1)).save(any())
        assertNotNull(result)
        assertEquals(result.originAccountIban, iban)
        assertEquals(result.destinationAccountIban, iban)
    }

    @Test
    fun accountNotFoundWhenTransferRequestTest() {
        val exception = org.junit.jupiter.api.assertThrows<AccountNotFoundProblem> {
            moneyTransferService.moneyTransfer(amountTransfer, uuid, uuid)
        }
        assertEquals("Not found: Account $uuid not found.", exception.message)
    }

    @Test
    fun accountWithNotEnoughBalanceWhenTransferRequestTest() {
        `when`(accountRepository.findByIban(any())).thenReturn(createAccount())
        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(accountRepository.saveAndFlush(any())).thenReturn(createAccount())
        `when`(transactionRepository.save(any())).thenReturn(createTransaction())

        val exception = org.junit.jupiter.api.assertThrows<BalanceNotEnoughProblem> {
            moneyTransferService.moneyTransfer(amount, uuid, uuid)
        }
        assertEquals("Account with insufficient balance: Account $uuid does not have enough balance.", exception.message)
    }

}