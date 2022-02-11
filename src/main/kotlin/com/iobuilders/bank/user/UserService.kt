package com.iobuilders.bank.user

import com.iobuilders.bank.user.exceptions.UserNotFoundException
import com.iobuilders.bank.user.exceptions.UsernameAlreadyExistsException
import com.iobuilders.bank.user.model.User
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser (
        username: String,
        firstName: String?,
        lastName: String?,
        email: String?,
    ) : User {
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
            throw UsernameAlreadyExistsException("Username $username already exists")
        }
    }

    fun getUserById(userId: UUID): User {
        return userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException("User with id:$userId not found")

        //return userRepository.getById(userId) ?: throw UserNotFoundException("User with id:$userId not found")
    }

    fun deleteUser(userId: UUID) {
        userRepository.deleteById(userId)
    }
}
