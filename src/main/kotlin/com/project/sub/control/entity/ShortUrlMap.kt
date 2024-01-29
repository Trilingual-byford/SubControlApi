package com.project.sub.control.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
import java.util.*

@Entity
@Table(name = "short_url_map")
data class ShortUrlMap(
    @Id
    @Column(name = "short_url")
    val shortUrl: String,
    @Column(name = "user_id")
    val userId: UUID?,
    @Column(name = "subscription_url")
    var subscriptionUrl: String?,
    @Column(name = "expire_date")
    val expireDate: Date?,
    @Column(name = "creation_time", nullable = false)
    @CreationTimestamp
    val creationTime: Date?,
)

