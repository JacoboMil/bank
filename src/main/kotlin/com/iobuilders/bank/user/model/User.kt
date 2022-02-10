package com.iobuilders.bank.user.model

import java.util.*

data class User (

    val id: UUID,
    val username: String,
    val name: String ?= null,
    val lastname: String ?= null

)
