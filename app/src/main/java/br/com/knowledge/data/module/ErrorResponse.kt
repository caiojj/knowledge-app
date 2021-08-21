package br.com.knowledge.data.module

data class ErrorResponse(
    val status: Long,
    val code: String,
    val message: String
)