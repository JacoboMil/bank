package com.iobuilders.bank.transaction.domain

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.domain.AccountRepository
import com.iobuilders.bank.account.domain.exception.AccountNotFoundException
import com.iobuilders.bank.transaction.domain.exception.BalanceNotEnoughException
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

        val result = moneyTransferService.moneyTransfer(amountTransfer, iban, iban)

        verify(accountRepository, times(2)).findByIban(any())
        verify(accountRepository, times(2)).findById(any())
        verify(accountRepository, times(2)).saveAndFlush(any())
        verify(transactionRepository, times(1)).save(any())
        assertNotNull(result)
        assertEquals(result.originAccountIban, iban)
        assertEquals(result.destinationAccountIban, iban)
    }

    @Test
    fun accountNotFoundWhenTransferRequestTest() {
        val exception = org.junit.jupiter.api.assertThrows<AccountNotFoundException> {
            moneyTransferService.moneyTransfer(amountTransfer, iban, iban)
        }
        assertEquals("Account with IBAN: $iban not exists", exception.message)
    }

    @Test
    fun accountWithNotEnoughBalanceWhenTransferRequestTest() {
        `when`(accountRepository.findByIban(any())).thenReturn(createAccount())
        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(accountRepository.saveAndFlush(any())).thenReturn(createAccount())
        `when`(transactionRepository.save(any())).thenReturn(createTransaction())

        val exception = org.junit.jupiter.api.assertThrows<BalanceNotEnoughException> {
            moneyTransferService.moneyTransfer(amount, iban, iban)
        }
        assertEquals("Account: $iban does not have enough balance.", exception.message)
    }

}