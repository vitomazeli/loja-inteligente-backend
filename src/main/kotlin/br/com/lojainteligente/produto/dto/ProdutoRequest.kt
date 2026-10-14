
package br.com.lojainteligente.produto.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class ProdutoRequest(

    @field:NotBlank(message = "O nome do produto é obrigatório")
    @field:Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    val nome: String,

    @field:Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    val descricao: String? = null,

    @field:NotBlank(message = "O código de barras é obrigatório")
    @field:Size(max = 50, message = "O código de barras deve ter no máximo 50 caracteres")
    val codigoBarras: String,

    @field:DecimalMin(
        value = "0.00",
        inclusive = false,
        message = "O preço deve ser maior que zero"
    )
    val preco: BigDecimal,

    @field:Positive(message = "A categoria é obrigatória")
    val categoriaId: Long,

    val ativo: Boolean = true
)
