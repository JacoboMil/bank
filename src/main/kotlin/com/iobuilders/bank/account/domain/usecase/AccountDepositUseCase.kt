package com.iobuilders.bank.account.domain.usecase

import com.iobuilders.bank.account.domain.model.Account
import java.math.BigDecimal
import java.util.*

interface AccountDepositUseCase {

    fun accountDeposit(
        accountId: UUID,
        amount: BigDecimal,
    ): Account

}