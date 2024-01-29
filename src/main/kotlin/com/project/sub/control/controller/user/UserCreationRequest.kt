package com.project.sub.control.controller.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty


data class UserCreationRequest(
    @field:NotEmpty
    val userName: String,

    @field:Email
    val email: String,
)
