package com.iobuilders.bank.account.domain

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.domain.exception.AccountNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.math.BigDecimal
import java.util.*

@RunWith(MockitoJUnitRunner::class)
internal class AccountDepositServiceTest: TestUtils() {

    private lateinit var accountDepositService: AccountDepositService

    @Mock
    lateinit var accountRepository: AccountRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        accountDepositService = AccountDepositService(accountRepository)
    }

    @Test
    fun accountDepositTest() {
        val newAmount = amount.multiply(BigDecimal(2))
        val accountUpdated = createAccount()
        accountUpdated.amount = newAmount

        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(accountRepository.saveAndFlush(any())).thenReturn(accountUpdated)

        val result = accountDepositService.accountDeposit(uuid, amount)

        verify(accountRepository, times(1)).findById(any())
        verify(accountRepository, times(1)).saveAndFlush(any())
        assertEquals(result.amount, newAmount)
    }

    @Test
    fun accountNotFoundTest() {
        val exception = assertThrows<AccountNotFoundException> {
            accountDepositService.accountDeposit(uuid, amount)
        }
        assertEquals("Account with accountId: $uuid not found", exception.message)
    }
}