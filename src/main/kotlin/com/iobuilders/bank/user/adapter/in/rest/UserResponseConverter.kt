package com.iobuilders.bank.user.adapter.`in`.rest

import com.iobuilders.bank.model.UserResponse
import com.iobuilders.bank.user.domain.model.User
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class UserResponseConverter : Converter<User, UserResponse> {
    override fun convert(source: User): UserResponse {
        return UserResponse(
            userId = source.id,
            username = source.username,
            firstName = source.firstName,
            lastName = source.lastName,
            email = source.email,
        )
    }
}