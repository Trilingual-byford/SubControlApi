package com.project.sub.control.controller.orisuburl

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern


data class OriginalUrlCreationRequest(
    @field:Pattern(regexp = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$")
    val originalUrl: String,

    val subUrl: String?,

    @field:NotEmpty
    val addedBy: String,
)
