package com.project.sub.control.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.util.*

@Entity
@Table(name = "original_sub_url")
data class OriginalSubUrl(
    @Id
    @Column(name = "url_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val urlId: Int?,
    @Column(name = "original_url", nullable = false)
    val originalUrl: String,
    @Column(name = "subscription_url", nullable = false, length = 10000)
    val subscriptionUrl: String,
    val connectionStatus: ConnectStatus?,
    @Column(name = "added_by", nullable = false)
    val addedBy: String,
    val expireDate: Date?,
    @Column(name = "creation_time", nullable = false)
    @CreationTimestamp
    val creationTime: Date?
)

enum class ConnectStatus {
    CONNECT,
    DISCONNECTED,
    UNAVAILABLE,
    EXPIRED
}