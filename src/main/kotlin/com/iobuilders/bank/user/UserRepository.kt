package com.iobuilders.bank.user

import org.h2.engine.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, UUID> {

}
