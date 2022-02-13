package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.BaseExceptionHandler
import com.iobuilders.bank.account.domain.exception.AccountNotFoundException
import com.iobuilders.bank.account.domain.exception.UserNotFoundException
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.zalando.problem.Problem
import org.zalando.problem.Status

@ControllerAdvice
class AccountExceptionHandler : BaseExceptionHandler() {

    companion object {
        private val log = getLogger(AccountExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [(AccountNotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAccountNotFound(ex: AccountNotFoundException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(Status.NOT_FOUND, ex, ex.title)
    }

    @ExceptionHandler(value = [(UserNotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(Status.NOT_FOUND, ex, ex.title)
    }


}
