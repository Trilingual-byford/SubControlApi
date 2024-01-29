package com.project.sub.control.controller.user

import com.project.sub.control.entity.ShortUrlMap
import com.project.sub.control.entity.User
import com.project.sub.control.repository.ShortUrlMapRepository
import com.project.sub.control.repository.UserRepository
import com.project.sub.control.utils.encodeToShortUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
@Validated
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var shortUrlMapRepository: ShortUrlMapRepository

    @PostMapping()
    fun create(@RequestBody @Validated request: UserCreationRequest): ResponseEntity<User> {
        val shortUrl = request.userName.encodeToShortUrl(8)
        val userId = UUID.randomUUID()

        val user = userRepository.save(User(userId, request.userName, shortUrl, request.email, null))
        shortUrlMapRepository.save(ShortUrlMap(shortUrl, userId, null, null, null))
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }


    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
        userRepository.deleteById(uuid)
        return ResponseEntity.noContent()
            .build()
    }

    @GetMapping
    fun listAll(): ResponseEntity<List<User>> {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll())
    }


}