package com.iobuilders.bank.user

import com.iobuilders.bank.TestUtils
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
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
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
    fun getUserByIdTest() {
        `when`(userRepository.findById(any())).thenReturn(Optional.of(createUser()))

        val result = userService.getUserById(uuid)

        verify(userRepository, times(1)).findById(uuid)
        assertNotNull(result)
        assertEquals(result.id, uuid)
    }

    @Test
    fun deleteUserTest() {
        doNothing().`when`(userRepository).deleteById(uuid)

        userService.deleteUser(uuid)

        verify(userRepository, times(1)).deleteById(uuid)
    }

}
