package com.iobuilders.bank.user.model

import java.util.*
import javax.persistence.*

@Entity
class User (

    @Id
    @Column(name = "id", unique = true)
    val id: UUID,
    @Column(name = "username", unique = true)
    val username: String,
    @Column(name = "firstname")
    val firstName: String? = null,
    @Column(name = "lastname")
    val lastName: String? = null,
    @Column(name = "email")
    val email: String? = null

)
