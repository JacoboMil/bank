package com.iobuilders.bank.account.adapter.`in`.rest

import com.iobuilders.bank.BaseExceptionHandler
import com.iobuilders.bank.account.exceptions.AccountNotFoundException
import org.slf4j.LoggerFactory
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
        private val log = LoggerFactory.getLogger(AccountExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [(AccountNotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFound(ex: AccountNotFoundException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(Status.NOT_FOUND, ex, ex.title)
    }

}
