package com.iobuilders.bank.transaction

import com.iobuilders.bank.transaction.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionRepository: JpaRepository<Transaction, UUID> {
}