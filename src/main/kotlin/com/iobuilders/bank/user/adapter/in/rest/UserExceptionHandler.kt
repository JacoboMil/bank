package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.BaseExceptionHandler
import com.iobuilders.bank.user.exceptions.UserNotFoundException
import com.iobuilders.bank.user.exceptions.UsernameAlreadyExistsException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.zalando.problem.Problem

@ControllerAdvice
class UserExceptionHandler : BaseExceptionHandler() {

    companion object {
        private val log = LoggerFactory.getLogger(UserExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [(UserNotFoundException::class)])
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(HttpStatus.NOT_FOUND, ex, ex.title)
    }

    @ExceptionHandler(value = [(UsernameAlreadyExistsException::class)])
    fun handleUsernameAlreadyExists(ex: UsernameAlreadyExistsException): ResponseEntity<Problem> {
        log.warn(ex.message)
        return problemResponseBuilder(HttpStatus.CONFLICT, ex, ex.title)
    }

}
