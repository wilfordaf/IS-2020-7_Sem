package ru.quipy.controllers.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserCreateDto(
    @NotBlank
    @Size(min = 2, max = 50, message = "Nickname must be from 2 to 50 symbols long")
    val nickname: String,

    @Size(min = 2, max = 50, message = "User name must be from 2 to 50 symbols long")
    val userName: String?,

    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be from 8 to 20 symbols long")
    val password: String
)