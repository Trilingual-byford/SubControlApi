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
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*


@RestController
@RequestMapping("/api/short_url")
@Validated
class ShortUrlController {

    @Autowired
    lateinit var shortUrlMapRepository: ShortUrlMapRepository

    @Autowired
    lateinit var originalSubUrlRepository: OriginalSubUrlRepository

    private val restTemplate = RestTemplate()


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

    fun decode(url: String) = URLDecoder.decode(url, "UTF-8")
    fun encode(url: String) = URLEncoder.encode(url, "UTF-8")

    @GetMapping("test")
    fun decoderTestEndpoint(): ResponseEntity<String> {
        val responseEntity = restTemplate.getForEntity(
            URI("https://nanbeicloud.net/api/v1/client/subscribe?token=f32205231752adfee8d8a2ee37028c3f&flag=shadowrocket"),
            String::class.java
        )
        responseEntity.body.let {
            println("Before decode")
            println(it)
            val decodedBytes: ByteArray = Base64.getDecoder().decode(it)
            val decodedString = String(decodedBytes)
            println("After decode")
            println(decodedString)
            println("After utf8 encode")
            val utf8String = String(decodedString.toByteArray(), Charsets.UTF_8)
            println(utf8String)
            println("After url decode")
            val decodedUrl = decode(decodedString)
            println(decodedUrl)
            println("After url encode")
            val encodedUrl = encode(decodedUrl)
            println(encodedUrl)
            return ResponseEntity.status(HttpStatus.OK).body(decodedUrl)
        }
        return ResponseEntity.status(HttpStatus.OK).body("  ")
    }
}