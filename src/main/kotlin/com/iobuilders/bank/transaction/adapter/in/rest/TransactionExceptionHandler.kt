package com.iobuilders.bank.transaction.adapter.`in`.rest

import com.iobuilders.bank.BaseExceptionHandler
import com.iobuilders.bank.transaction.domain.exception.BalanceNotEnoughException
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.zalando.problem.Problem
import org.zalando.problem.Status

@ControllerAdvice
class TransactionExceptionHandler : BaseExceptionHandler() {

    companion object {
        private val log = getLogger(TransactionExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [(BalanceNotEnoughException::class)])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBalanceNotEnough(ex: BalanceNotEnoughException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(Status.BAD_REQUEST, ex, ex.title)
    }

}