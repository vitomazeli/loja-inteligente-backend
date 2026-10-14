
package br.com.lojainteligente.cliente.dto

import java.time.LocalDateTime

data class ClienteResponse(
    val id: Long?,
    val nome: String,
    val email: String,
    val telefone: String?,
    val ativo: Boolean,
    val dataCadastro: LocalDateTime
)
