package br.com.lojainteligente.auth.dto

data class LoginRequest(
    val email: String,
    val senha: String
)