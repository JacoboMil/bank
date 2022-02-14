package com.iobuilders.bank.transaction.domain.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.util.*

class BalanceNotEnoughProblem(accountId: UUID) : AbstractThrowableProblem(
    null,
    "Account with insufficient balance",
    Status.BAD_REQUEST, String.format("Account $accountId does not have enough balance.")
) {

    override fun getCause(): Exceptional?= super.cause

}