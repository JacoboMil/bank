package com.iobuilders.bank.account.domain

import com.iobuilders.bank.account.domain.model.Account
import com.iobuilders.bank.account.domain.usecase.CreateAccountUseCase
import com.iobuilders.bank.user.domain.UserRepository
import com.iobuilders.bank.account.domain.exception.UserNotFoundException
import org.iban4j.CountryCode
import org.iban4j.Iban
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class CreateAccountService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
): CreateAccountUseCase {
    override fun createAccount(userId: UUID): Account {
        userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException("The account was not created because user: $userId was not found.")
        return accountRepository.save(
            Account(
                id = UUID.randomUUID(),
                iban = Iban.random(CountryCode.ES).toString(),
                userId = userId,
                amount = BigDecimal.ZERO
            )
        )
    }
}