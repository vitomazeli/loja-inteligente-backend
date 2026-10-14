
package br.com.lojainteligente.categoria.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoriaRequest(

    @field:NotBlank(message = "O nome da categoria é obrigatório")
    @field:Size(
        max = 100,
        message = "O nome deve ter no máximo 100 caracteres"
    )
    val nome: String,

    @field:Size(
        max = 255,
        message = "A descrição deve ter no máximo 255 caracteres"
    )
    val descricao: String? = null,

    val ativo: Boolean = true,

    val ordemExibicao: Int = 0
)
