package com.iobuilders.bank.account

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.account.exceptions.AccountNotFoundException
import com.iobuilders.bank.model.UpdateAccountRequest
import com.iobuilders.bank.user.UserRepository
import com.iobuilders.bank.user.exceptions.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.math.BigDecimal
import java.util.*

@RunWith(MockitoJUnitRunner::class)
internal class AccountServiceTest: TestUtils() {

    private lateinit var accountService: AccountService

    @Mock
    lateinit var accountRepository: AccountRepository

    @Mock
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        accountService = AccountService(userRepository, accountRepository)
    }

    @Test
    fun createAccountTest() {
        `when`(userRepository.findById(any())).thenReturn(Optional.of(createUser()))
        `when`(accountRepository.save(any())).thenReturn(createAccount())

        accountService.createAccount(uuid)

        verify(accountRepository, times(1)).save(any())
    }

    @Test
    fun createAccountWithNotFoundUserTest() {
        `when`(userRepository.findById(any())).thenReturn(Optional.empty())

        val exception = assertThrows<UserNotFoundException> {
            accountService.createAccount(uuid)
        }
        assertEquals("The account was not created because user: $uuid was not found.", exception.message)
    }

    @Test
    fun getAccountByIdTest() {
        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))

        val result = accountService.getAccount(uuid)

        verify(accountRepository, times(1)).findById(uuid)
        assertNotNull(result)
        assertEquals(result.id, uuid)
    }

    @Test
    fun getAccountByIdWithInvalidIdTest() {
        `when`(accountRepository.findById(any())).thenReturn(Optional.empty())

        val exception = assertThrows<AccountNotFoundException> {
            accountService.getAccount(uuid)
        }
        assertEquals("Account with accountId: $uuid not found", exception.message)
    }

    @Test
    fun updateAccountAddAmountOperationTest() {
        val newAmount = amount.multiply(BigDecimal(2))
        val accountUpdated = createAccount()
        accountUpdated.amount = newAmount

        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(accountRepository.saveAndFlush(any())).thenReturn(accountUpdated)

        val result = accountService.updateAccount(uuid, amount, UpdateAccountRequest.Operation.add)

        verify(accountRepository, times(1)).findById(any())
        verify(accountRepository, times(1)).saveAndFlush(any())
        assertEquals(result.amount, newAmount)
    }

    @Test
    fun updateAccountSubstractAmountOperationTest() {
        val accountUpdated = createAccount()
        accountUpdated.amount = BigDecimal.ZERO

        `when`(accountRepository.findById(any())).thenReturn(Optional.of(createAccount()))
        `when`(accountRepository.saveAndFlush(any())).thenReturn(accountUpdated)

        val result = accountService.updateAccount(uuid, amount, UpdateAccountRequest.Operation.substract)

        verify(accountRepository, times(1)).findById(any())
        verify(accountRepository, times(1)).saveAndFlush(any())
        assertEquals(result.amount, BigDecimal.ZERO)
    }

    @Test
    fun getAccountsTest() {
        `when`(accountRepository.findAll()).thenReturn(listOf(createAccount()))
        `when`(userRepository.findById(any())).thenReturn(Optional.of(createUser()))

        val result = accountService.getAccounts(uuid)

        verify(accountRepository, times(1)).findAll()
        assertNotNull(result)
        assertEquals(result.count(), 1)
    }

}