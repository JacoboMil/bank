package com.iobuilders.bank.transaction.domain.model

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Transaction (

    @Id
    @Column(name = "id", unique = true)
    val id: UUID,
    @Column(name = "amount", unique = true)
    val amount: BigDecimal,
    @Column(name = "destinationAccountIban")
    val destinationAccountIban: String,
    @Column(name = "originAccountIban")
    val originAccountIban: String,
    @Column(name = "transactionDate")
    val transactionDate: OffsetDateTime

)
