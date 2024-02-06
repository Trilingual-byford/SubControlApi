package com.project.sub.control.controller.orisuburl

import com.project.sub.control.entity.ConnectStatus
import com.project.sub.control.entity.OriginalSubUrl
import com.project.sub.control.repository.OriginalSubUrlRepository
import com.project.sub.control.repository.ServerNodeRepository
import com.project.sub.control.utils.base64ToServerNodeDto
import com.project.sub.control.utils.toServerNodeList
import com.project.sub.control.utils.toSubUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate


@RestController
@RequestMapping("/api/ori_sub")
@Validated
class OriginalSubUrlController {
    @Autowired
    lateinit var originalSubUrlRepository: OriginalSubUrlRepository

    @Autowired
    lateinit var serverNodeRepository: ServerNodeRepository
    val origUrlParseService = "http://52.199.107.73:25500/sub?target=clash&url="

    private val restTemplate = RestTemplate()

    @PostMapping()
    fun create(@RequestBody @Validated request: OriginalUrlCreationRequest): ResponseEntity<out Any> {

        var subscriptionUrl = ""

        val responseEntity = restTemplate.getForEntity(request.originalUrl, String::class.java)
        if (responseEntity.statusCode != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseEntity.body)
        }
        responseEntity.body?.base64ToServerNodeDto()?.let {
            serverNodeRepository.saveAll(it.toServerNodeList(request.originalUrl))
            subscriptionUrl = it.toSubUrl(origUrlParseService)
        }
        subscriptionUrl.let {
            println("subscriptionUrl: ${it.length}))")
        }
        val originalSubUrl = originalSubUrlRepository.save(
            OriginalSubUrl(
                null,
                request.originalUrl,
                subscriptionUrl,
                ConnectStatus.CONNECT,
                "BeYOND",
                null,
                null
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(originalSubUrl)
    }


    @DeleteMapping("/{url_id}")
    fun deleteByUrlId(@PathVariable url_id: Int): ResponseEntity<Boolean> {
        originalSubUrlRepository.deleteById(url_id)
        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping
    fun listAll(): ResponseEntity<MutableList<OriginalSubUrl>> {
        return ResponseEntity.status(HttpStatus.OK).body(originalSubUrlRepository.findAll())
    }

}