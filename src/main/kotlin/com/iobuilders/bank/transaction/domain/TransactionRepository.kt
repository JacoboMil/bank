package com.iobuilders.bank.transaction.domain

import com.iobuilders.bank.transaction.domain.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionRepository: JpaRepository<Transaction, UUID> {
    fun findByOriginAccountIban(iban: String): List<Transaction>
    fun findByDestinationAccountIban(iban: String): List<Transaction>

}