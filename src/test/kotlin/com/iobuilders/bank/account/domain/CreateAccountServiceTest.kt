package com.iobuilders.bank.account.domain

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.user.domain.UserRepository
import com.iobuilders.bank.account.domain.exception.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
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
internal class CreateAccountServiceTest: TestUtils() {

    private lateinit var createAccountService: CreateAccountService

    @Mock
    lateinit var accountRepository: AccountRepository

    @Mock
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        createAccountService = CreateAccountService(userRepository, accountRepository)
    }

    @Test
    fun createAccountTest() {
        `when`(userRepository.findById(any())).thenReturn(Optional.of(createUser()))
        `when`(accountRepository.save(any())).thenReturn(createAccount())

        createAccountService.createAccount(uuid)

        verify(accountRepository, times(1)).save(any())
    }

    @Test
    fun createAccountWithNotFoundUserTest() {
        val exception = org.junit.jupiter.api.assertThrows<UserNotFoundException> {
            createAccountService.createAccount(uuid)
        }
        assertEquals("The account was not created because user: $uuid was not found.", exception.message)
    }
}