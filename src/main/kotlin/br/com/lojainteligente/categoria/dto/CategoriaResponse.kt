package br.com.lojainteligente.categoria.dto

data class CategoriaResponse(
    val id: Long?,
    val nome: String,
    val descricao: String?,
    val ativo: Boolean
)