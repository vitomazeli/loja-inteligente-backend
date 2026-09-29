package br.com.lojainteligente.venda.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class VendaResponse(
    val id: Long?,
    val clienteId: Long?,
    val clienteNome: String?,
    val dataVenda: LocalDateTime,
    val total: BigDecimal,
    val itens: List<ItemVendaResponse>
)