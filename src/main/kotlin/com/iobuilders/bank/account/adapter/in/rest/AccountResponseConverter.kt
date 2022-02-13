package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.model.AccountBalanceResponse
import com.iobuilders.bank.model.AccountResponse
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

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
class AccountBalanceResponseConverter : Converter<Account, AccountBalanceResponse> {
    override fun convert(source: Account): AccountBalanceResponse {
        return AccountBalanceResponse(
            accountId = source.id,
            iban = source.iban,
            balance = source.amount,
        )
    }
}

