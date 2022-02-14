package com.iobuilders.bank.transaction.domain.usecase

import com.iobuilders.bank.transaction.domain.model.Transaction
import java.math.BigDecimal
import java.util.*

interface MoneyTransferUseCase {

    fun moneyTransfer(
        amount: BigDecimal,
        destinationAccountId: UUID,
        originAccountId: UUID
    ): Transaction

}