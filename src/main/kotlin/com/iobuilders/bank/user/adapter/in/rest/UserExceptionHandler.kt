package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.BaseExceptionHandler
import com.iobuilders.bank.user.domain.exception.UsernameAlreadyExistsException
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.zalando.problem.Problem
import org.zalando.problem.Status

@ControllerAdvice
class UserExceptionHandler : BaseExceptionHandler() {

    companion object {
        private val log = getLogger(UserExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [(UsernameAlreadyExistsException::class)])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUsernameAlreadyExists(ex: UsernameAlreadyExistsException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(Status.CONFLICT, ex, ex.title)
    }
}