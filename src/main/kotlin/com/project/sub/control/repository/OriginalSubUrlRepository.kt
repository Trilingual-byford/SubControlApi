package com.project.sub.control.repository

import com.project.sub.control.entity.OriginalSubUrl
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OriginalSubUrlRepository : JpaRepository<OriginalSubUrl, Int> {
    fun findOriginalSubUrlByUrlId(originalUrl: Int): Optional<OriginalSubUrl>
}
