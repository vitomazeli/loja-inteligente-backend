
package br.com.lojainteligente.categoria.dto

import java.time.LocalDateTime

data class CategoriaResponse(
    val id: Long?,
    val nome: String,
    val descricao: String?,
    val ativo: Boolean,
    val dataCadastro: LocalDateTime,
    val ordemExibicao: Int
)
