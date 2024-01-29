package com.project.sub.control.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    val userId: UUID,
    @Column(name = "user_name", nullable = false)
    val userName: String,
    @Column(name = "short_url", nullable = false)
    val shortUrl: String,
    val email: String,
    @Column(name = "creation_time", nullable = false)
    @CreationTimestamp
    val creationTime: Date?,
)
