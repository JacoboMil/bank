package com.iobuilders.bank.user

import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.user.exceptions.UserNotFoundException
import com.iobuilders.bank.user.exceptions.UsernameAlreadyExistsException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
internal class UserServiceTest : TestUtils() {


    private lateinit var userService: UserService

    @Mock
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        openMocks(this)
        userService = UserService(userRepository)
    }

    @Test
    fun createUserTest() {
        `when`(userRepository.save(any())).thenReturn(createUser())

        userService.createUser(text, null, null, null)

        verify(userRepository, times(1)).save(any())
    }

    @Test
    fun usernameConflictTest() {
        `when`(userRepository.save(any())).thenThrow(DataIntegrityViolationException::class.java)

        val exception = assertThrows<UsernameAlreadyExistsException> {
            userService.createUser(text, null, null, null)
        }
        assertEquals("Username $text already exists", exception.message)
    }

    @Test
    fun getUserByIdTest() {
        `when`(userRepository.findById(any())).thenReturn(Optional.of(createUser()))

        val result = userService.getUserById(uuid)

        verify(userRepository, times(1)).findById(uuid)
        assertNotNull(result)
        assertEquals(result.id, uuid)
    }

    @Test
    fun userNotFoundTest() {
        `when`(userRepository.findById(any())).thenReturn(Optional.empty())

        val exception = assertThrows<UserNotFoundException> {
            userService.getUserById(uuid)
        }
        assertEquals("User with id:$uuid not found", exception.message)
    }

    @Test
    fun deleteUserTest() {
        doNothing().`when`(userRepository).deleteById(uuid)

        userService.deleteUser(uuid)

        verify(userRepository, times(1)).deleteById(uuid)
    }

}
