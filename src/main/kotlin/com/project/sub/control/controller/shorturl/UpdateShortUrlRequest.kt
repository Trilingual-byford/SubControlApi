package com.project.sub.control.controller.shorturl

import jakarta.validation.constraints.NotNull

data class UpdateShortUrlRequest(
    @field:NotNull
    val subUrlId: Int,
)
