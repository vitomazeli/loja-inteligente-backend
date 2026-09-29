package br.com.lojainteligente.venda.dto

import java.math.BigDecimal

data class ItemVendaResponse(
    val id: Long?,
    val produtoId: Long?,
    val produtoNome: String?,
    val quantidade: Int,
    val precoUnitario: BigDecimal,
    val subtotal: BigDecimal
)