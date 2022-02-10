package com.iobuilders.bank.user.model

import java.util.*
import javax.persistence.*

@Entity
class User (

    @Id
    val id: UUID,
    @Column(unique = true)
    val username: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null

)
