package com.project.sub.control.repository

import com.project.sub.control.entity.ShortUrlMap
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShortUrlMapRepository : JpaRepository<ShortUrlMap, String> {
    fun findShortUrlMapByShortUrl(shortUrl: String): Optional<ShortUrlMap>
}
