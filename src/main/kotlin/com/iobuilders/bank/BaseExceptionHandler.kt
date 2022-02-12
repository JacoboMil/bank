package com.iobuilders.bank

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.zalando.problem.Problem
import org.zalando.problem.Status

open class BaseExceptionHandler {

    fun problemResponseBuilder(status: Status, ex: Exception, title: String): ResponseEntity<Problem> {
        val result = Problem.builder()
            .withTitle(title)
            .withStatus(status)
            .withDetail(ex.localizedMessage)
            .build()
        return ResponseEntity<Problem>(result, HttpStatus.valueOf(status.statusCode))
    }
}
