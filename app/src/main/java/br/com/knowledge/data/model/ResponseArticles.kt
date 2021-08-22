package br.com.knowledge.data.model

data class ResponseArticles(
    val data: List<Article>,
    val count: Int,
    val limit: Int,
)
