package com.iobuilders.bank.transaction.adapter.`in`.rest

import com.iobuilders.bank.model.TransactionResponse
import com.iobuilders.bank.model.TransactionsResponse
import com.iobuilders.bank.transaction.model.Transaction
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class TransactionResponseConverter : Converter<Transaction, TransactionResponse> {
    override fun convert(source: Transaction): TransactionResponse {
        return TransactionResponse(
            transactionId = source.id,
            amount = source.amount,
            destinationAccountIban = source.destinationAccountIban,
            originAccountIban = source.originAccountIban,
            transactionDate = source.transactionDate
        )
    }
}

@Component
class TransactionsResponseConverter(
    private val converter: TransactionResponseConverter
) : Converter<List<Transaction>, TransactionsResponse> {
    override fun convert(source: List<Transaction>): TransactionsResponse? {
        return TransactionsResponse(
            BigDecimal(source.count()),
            source.map { converter.convert(it) }
        )
    }
}
