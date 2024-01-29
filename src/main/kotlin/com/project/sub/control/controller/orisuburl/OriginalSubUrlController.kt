package com.project.sub.control.controller.orisuburl

import com.project.sub.control.entity.ConnectStatus
import com.project.sub.control.entity.OriginalSubUrl
import com.project.sub.control.repository.OriginalSubUrlRepository
import com.project.sub.control.utils.encodeUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/ori_sub")
@Validated
class OriginalSubUrlController {
    @Autowired
    lateinit var originalSubUrlRepository: OriginalSubUrlRepository
    val origUrlParseService = "http://52.199.107.73:25500/sub?target=clash&url="

    @PostMapping()
    fun create(@RequestBody @Validated request: OriginalUrlCreationRequest): ResponseEntity<OriginalSubUrl> {
        val encodeUrl = request.originalUrl.encodeUrl()

        var subscriptionUrl = origUrlParseService + encodeUrl

        request.subUrl.let {
            if (it != null) {
                subscriptionUrl = it
            }
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