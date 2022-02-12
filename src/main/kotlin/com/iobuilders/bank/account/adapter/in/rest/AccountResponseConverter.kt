package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.account.model.Account
import com.iobuilders.bank.model.AccountResponse
import com.iobuilders.bank.model.AccountsResponse
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class AccountResponseConverter : Converter<Account, AccountResponse> {
    override fun convert(source: Account): AccountResponse {
        return AccountResponse(
            accountId = source.id,
            iban = source.iban,
            amount = source.amount,
            owner = source.userId.toString()
        )
    }
}

@Component
class AccountsResponseConverter(
    private val converter: AccountResponseConverter
) : Converter<List<Account>, AccountsResponse> {
    override fun convert(source: List<Account>): AccountsResponse? {
        return AccountsResponse(
            BigDecimal(source.count()),
            source.map { converter.convert(it) }
        )
    }
}
