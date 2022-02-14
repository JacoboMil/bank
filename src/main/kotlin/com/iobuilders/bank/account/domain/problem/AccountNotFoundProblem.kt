package com.iobuilders.bank.account.domain.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.util.*

class AccountNotFoundProblem(accountId: UUID) : AbstractThrowableProblem(
    null,
    "Not found",
    Status.NOT_FOUND, String.format("Account $accountId not found.")
) {

    override fun getCause(): Exceptional?= super.cause

}