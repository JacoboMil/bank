package com.iobuilders.bank.user.adapter.`in`.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iobuilders.bank.TestUtils
import com.iobuilders.bank.user.UserService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringRunner::class)
@WebMvcTest(UserController::class)
internal class UserControllerTest : TestUtils() {

    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper
    private lateinit var userResponseConverter: UserResponseConverter
    private lateinit var userController: UserController
    @MockBean
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(UserController(userService, userResponseConverter)).build()
        mapper = jacksonObjectMapper()
        userResponseConverter = UserResponseConverter()
        userController = UserController(userService, userResponseConverter)
    }

    @Test
    fun whenPostRequestWithCorrectUser_thenReturnsStatus201() {
        `when`(userService.createUser(any(), any(), any(), any())).thenReturn(createUser())

        mockMvc.perform(post("/v1/users")
            .content(this.mapper.writeValueAsString(createUserRequest()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.email").value(email))
    }

    @Test
    fun whenGetRequestWithValidId_thenReturn200() {
        `when`(userService.getUserById(uuid)).thenReturn(createUser())

        mockMvc.perform(get("/v1/users/$uuid")
            .content(this.mapper.writeValueAsString(uuid))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.email").value(email))
    }

    @Test
    fun whenDeleteRequestWithValidId_thenReturn200() {
        mockMvc.perform(delete("/v1/users/$uuid")
            .content(this.mapper.writeValueAsString(uuid))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }
}