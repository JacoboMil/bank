package com.iobuilders.bank.user.domain

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.user.domain.exception.UsernameAlreadyExistsException
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
import org.springframework.dao.DataIntegrityViolationException

@RunWith(MockitoJUnitRunner::class)
internal class RegisterUserServiceTest : TestUtils() {


    private lateinit var registerUserService: RegisterUserService

    @Mock
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        registerUserService = RegisterUserService(userRepository)
    }

    @Test
    fun registerUserTest() {
        `when`(userRepository.save(any())).thenReturn(createUser())

        registerUserService.registerUser(text, null, null, null)

        verify(userRepository, times(1)).save(any())
    }

    @Test
    fun usernameConflictTest() {
        `when`(userRepository.save(any())).thenThrow(DataIntegrityViolationException::class.java)

        val exception = org.junit.jupiter.api.assertThrows<UsernameAlreadyExistsException> {
            registerUserService.registerUser(text, null, null, null)
        }
        assertEquals("Username $text already exists", exception.message)
    }
}