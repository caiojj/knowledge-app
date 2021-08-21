package br.com.knowledge.data.module

data class ResponseLogin(
    val id: Long,
    val name: String,
    val email: String,
    val admin: Boolean,
    val iat: Long,
    val exp: Long,
    val token: String,
)