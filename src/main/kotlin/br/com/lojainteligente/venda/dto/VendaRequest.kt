package br.com.lojainteligente.venda.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class VendaRequest(

    @field:Positive(message = "O cliente é obrigatório")
    val clienteId: Long,

    @field:NotEmpty(message = "A venda deve possuir pelo menos um produto")
    @field:Valid
    val itens: List<ItemVendaRequest>
)