package com.project.sub.control.repository

import com.project.sub.control.entity.ServerNode
import org.springframework.data.jpa.repository.JpaRepository

interface ServerNodeRepository : JpaRepository<ServerNode, Int> {
    fun findServerNodesByOriginalSubUrl(originalSubUrl: String): List<ServerNode>
}
