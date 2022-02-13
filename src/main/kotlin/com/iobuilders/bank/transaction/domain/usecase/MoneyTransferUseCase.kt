package com.iobuilders.bank.transaction.domain.usecase

import com.iobuilders.bank.transaction.domain.model.Transaction
import java.math.BigDecimal

interface MoneyTransferUseCase {

    fun moneyTransfer(
        amount: BigDecimal,
        destinationAccountIban: String,
        originAccountIban: String
    ): Transaction

}