package com.iobuilders.bank

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.problem.jackson.ProblemModule
import org.zalando.problem.violations.ConstraintViolationProblemModule
import java.io.IOException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


@Configuration
class JacksonConfiguration(
    @Value("\${problem.stackTraces}")
    private val stackTraces: Boolean
) {

    @Bean
    fun objectMapper(): ObjectMapper? {
        val simpleModule = SimpleModule()
        simpleModule.addSerializer(OffsetDateTime::class.java, object : JsonSerializer<OffsetDateTime?>() {
            @Throws(IOException::class, JsonProcessingException::class)
            override fun serialize(
                offsetDateTime: OffsetDateTime?,
                jsonGenerator: JsonGenerator,
                serializerProvider: SerializerProvider?
            ) {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime))
            }
        })

        return ObjectMapper().registerModules(
            ProblemModule().withStackTraces(stackTraces),
            ConstraintViolationProblemModule(),
            JavaTimeModule(),
            simpleModule,
            KotlinModule()
        ).setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
}
