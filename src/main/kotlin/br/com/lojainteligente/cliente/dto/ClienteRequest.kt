package br.com.lojainteligente.cliente.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ClienteRequest(

    @field:NotBlank(message = "O nome do cliente é obrigatório")
    val nome: String,

    @field:NotBlank(message = "O e-mail é obrigatório")
    @field:Email(message = "O e-mail informado é inválido")
    val email: String
)