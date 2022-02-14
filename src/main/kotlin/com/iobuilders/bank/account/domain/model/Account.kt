package com.iobuilders.bank.account.domain.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Account (

    @Id
    @Column(name = "id", unique = true)
    val id: UUID,
    @Column(name = "iban", unique = true)
    val iban: String,
    @Column(name = "userId")
    val userId: UUID,
    @Column(name = "amount")
    var amount: BigDecimal

)
