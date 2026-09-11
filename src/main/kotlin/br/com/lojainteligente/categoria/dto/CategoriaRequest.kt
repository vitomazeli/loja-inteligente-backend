package br.com.lojainteligente.categoria.dto

import jakarta.validation.constraints.NotBlank

data class CategoriaRequest(

    @field:NotBlank(message = "O nome da categoria é obrigatório")
    val nome: String,

    val descricao: String? = null,

    val ativo: Boolean = true
)