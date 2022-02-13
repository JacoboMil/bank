package com.iobuilders.bank.account.domain

import com.iobuilders.bank.account.domain.exception.AccountNotFoundException
import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.account.domain.usecase.AccountDepositUseCase
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountDepositService(
    private val accountRepository: AccountRepository
): AccountDepositUseCase {
    override fun accountDeposit(accountId: UUID, amount: BigDecimal): Account {
        var account = accountRepository.findByIdOrNull(accountId)  ?: throw AccountNotFoundException("Account with accountId: $accountId not found")
        var newAmount = account.amount.plus(amount)
        account.amount = newAmount
        return accountRepository.saveAndFlush(account)
    }
}