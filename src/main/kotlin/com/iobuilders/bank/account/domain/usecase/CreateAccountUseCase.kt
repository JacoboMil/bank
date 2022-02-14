package com.iobuilders.bank.account.domain.usecase

import com.iobuilders.bank.account.domain.model.Account
import java.util.*

interface CreateAccountUseCase {

    fun createAccount(userId: UUID): Account

}