package br.com.lojainteligente.produto.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ProdutoRequest(

    @field:NotBlank(message = "O nome do produto é obrigatório")
    val nome: String,

    val descricao: String? = null,

    @field:NotBlank(message = "O código de barras é obrigatório")
    val codigoBarras: String,

    @field:DecimalMin(
        value = "0.0",
        message = "O preço não pode ser negativo"
    )
    val preco: BigDecimal,

    @field:Positive(message = "A categoria é obrigatória")
    val categoriaId: Long,

    val ativo: Boolean = true
)