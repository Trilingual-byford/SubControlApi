package com.project.sub.control.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.sql.Date

@Entity
@Table(name = "server_node")
data class ServerNode(
    @Id
    @Column(name = "node_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val nodeId: Int,
    @Column(name = "server_url", nullable = false)
    val serverUrl: String,
    @Column(name = "comment", nullable = true)
    val comment: String?,
    @Column(name = "short_url", nullable = true)
    val shortUrl: String?,
    @Column(name = "original_sub_url", nullable = true, length = 10000)
    val originalSubUrl: String?,
    @Column(name = "creation_time", nullable = false)
    @CreationTimestamp
    val creationTime: Date?,
)
