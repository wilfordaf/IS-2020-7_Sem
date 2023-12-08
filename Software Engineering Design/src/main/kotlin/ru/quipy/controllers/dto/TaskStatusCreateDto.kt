package ru.quipy.controllers.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class TaskStatusCreateDto(
    @NotBlank
    @Size(min = 2, max = 50, message = "Title must be 2 to 50 characters long")
    val title: String,

    @Min(value = 0, message = "Color RGB must be a non-negative value")
    val colorRgb: Int
)