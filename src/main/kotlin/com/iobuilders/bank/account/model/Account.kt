package com.iobuilders.bank.account.model

import java.math.BigDecimal
import java.util.*

data class Account (

    val id: UUID,
    val iban: String,
    val userId: UUID,
    val amount: BigDecimal

)
