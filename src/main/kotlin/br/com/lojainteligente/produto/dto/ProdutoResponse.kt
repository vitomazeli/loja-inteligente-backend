package br.com.lojainteligente.produto.dto

import java.math.BigDecimal

data class ProdutoResponse(

    val id: Long?,

    val nome: String,

    val descricao: String?,

    val codigoBarras: String,

    val preco: BigDecimal,

    val categoriaId: Long?,

    val categoriaNome: String?,

    val ativo: Boolean
)