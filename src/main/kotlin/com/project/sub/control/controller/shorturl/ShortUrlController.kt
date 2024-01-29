package com.project.sub.control.controller.shorturl

import com.project.sub.control.entity.ShortUrlMap
import com.project.sub.control.repository.OriginalSubUrlRepository
import com.project.sub.control.repository.ShortUrlMapRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
@RequestMapping("/api/short_url")
@Validated
class ShortUrlController {

    @Autowired
    lateinit var shortUrlMapRepository: ShortUrlMapRepository

    @Autowired
    lateinit var originalSubUrlRepository: OriginalSubUrlRepository

    val shortUrlDomain = "http://localhost:8080/"

    @PatchMapping("/{short_url_id}")
    fun boundSubUrlByUrlId(
        @PathVariable short_url_id: String,
        @RequestBody @Valid request: UpdateShortUrlRequest
    ): ResponseEntity<String> {
        val originalSubUrlOptional = originalSubUrlRepository.findOriginalSubUrlByUrlId(request.subUrlId)
        val shortUrlMapObj = shortUrlMapRepository.findShortUrlMapByShortUrl(short_url_id)
        if (originalSubUrlOptional.isEmpty || shortUrlMapObj.isEmpty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubUrl were not found")
        } else {
            val shortUrlMap = shortUrlMapObj.get()
            shortUrlMap.subscriptionUrl = originalSubUrlOptional.get().subscriptionUrl
            shortUrlMapRepository.save(shortUrlMap)
            return ResponseEntity.status(HttpStatus.OK).body("subscription url updated for ${short_url_id}")
        }
    }

    @GetMapping()
    fun findAllSubUrl(): ResponseEntity<List<ShortUrlMap>> {
        val shortUrlMaps = shortUrlMapRepository.findAll()
        return ResponseEntity.status(HttpStatus.OK).body(shortUrlMaps)
    }

    @GetMapping("{short_url}")
    fun directShortUrlToSubUrl(@PathVariable short_url: String): ResponseEntity<String> {
        val shortUrlMaps = shortUrlMapRepository.findShortUrlMapByShortUrl(short_url)
        if (shortUrlMaps.isEmpty || shortUrlMaps.get().shortUrl.isEmpty()) throw Exception("${short_url} is not found")
        if (shortUrlMaps.get().subscriptionUrl == null) {
            throw Exception("Subscription url for ${short_url} is not found")
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortUrlMaps.get().subscriptionUrl)).build()
    }
}