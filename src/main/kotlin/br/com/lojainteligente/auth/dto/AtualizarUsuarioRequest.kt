package br.com.lojainteligente.auth.dto

data class AtualizarUsuarioRequest(
    val nome: String,
    val email: String,
    val perfil: String,
    val ativo: Boolean
)