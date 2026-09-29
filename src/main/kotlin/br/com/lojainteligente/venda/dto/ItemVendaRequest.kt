package br.com.lojainteligente.venda.dto

import jakarta.validation.constraints.Positive

data class ItemVendaRequest(

    @field:Positive(message = "O ID do produto deve ser válido")
    val produtoId: Long,

    @field:Positive(message = "A quantidade deve ser maior que zero")
    val quantidade: Int
)