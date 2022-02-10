package com.iobuilders.bank.account.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Account (

    @Id
    val id: UUID,
    val iban: String,
    val userId: UUID,
    val amount: BigDecimal

)
