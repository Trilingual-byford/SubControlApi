package com.project.sub.control.repository

import com.project.sub.control.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository:JpaRepository<User,UUID> {
    fun findUserByUserId(userId:UUID): Optional<User>
}
