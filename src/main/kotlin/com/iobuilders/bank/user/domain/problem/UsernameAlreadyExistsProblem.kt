package com.iobuilders.bank.user.domain.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status

class UsernameAlreadyExistsProblem(username: String) : AbstractThrowableProblem(
    null,
    "Username Already Exists",
    Status.CONFLICT, String.format("Username $username already exists")
) {

    override fun getCause(): Exceptional?= super.cause

}
