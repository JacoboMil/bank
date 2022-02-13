package com.iobuilders.bank.account.domain

import com.iobuilders.bank.account.domain.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: JpaRepository<Account, UUID> {
    fun findByIban(iban: String): Account?
}
