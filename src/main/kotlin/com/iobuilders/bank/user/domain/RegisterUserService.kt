package com.iobuilders.bank.user.domain

import com.iobuilders.bank.user.domain.problem.UsernameAlreadyExistsProblem
import com.iobuilders.bank.user.domain.usecase.RegisterUserUseCase
import com.iobuilders.bank.user.domain.model.User
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisterUserService(
    private val userRepository: UserRepository
): RegisterUserUseCase {
    override fun registerUser(username: String, firstName: String?, lastName: String?, email: String?): User {
        try {
            return userRepository.save(
                User(
                    id = UUID.randomUUID(),
                    username = username,
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            )
        } catch (ex: DataIntegrityViolationException) {
            throw UsernameAlreadyExistsProblem(username)
        }
    }
}