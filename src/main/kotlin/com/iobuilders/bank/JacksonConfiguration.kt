package com.iobuilders.bank

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.problem.jackson.ProblemModule
import org.zalando.problem.violations.ConstraintViolationProblemModule

@Configuration
class JacksonConfiguration(
    @Value("\${problem.stackTraces}")
    private val stackTraces: Boolean
) {

    @Bean
    fun objectMapper(): ObjectMapper? {
        return ObjectMapper().registerModules(
            ProblemModule().withStackTraces(stackTraces),
            ConstraintViolationProblemModule(),
            KotlinModule()
        ).setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
}
