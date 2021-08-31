package br.com.knowledge.data.model

data class ResponseLogin(
    val id: Long,
    val name: String,
    val email: String,
    val admin: Boolean,
    val imageUrl: String?,
    val iat: Long,
    val exp: Long,
    val token: String,
)