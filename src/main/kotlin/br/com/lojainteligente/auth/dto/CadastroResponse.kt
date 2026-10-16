
package br.com.lojainteligente.auth.dto

data class CadastroResponse(
    val id: Long?,
    val nome: String,
    val email: String,
    val perfil: String,
    val ativo: Boolean
)
