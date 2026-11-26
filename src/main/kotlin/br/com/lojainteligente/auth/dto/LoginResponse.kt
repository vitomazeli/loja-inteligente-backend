package br.com.lojainteligente.auth.dto

data class LoginResponse(
    val token: String
)

data class ErroResponse(
    val mensagem: String
)