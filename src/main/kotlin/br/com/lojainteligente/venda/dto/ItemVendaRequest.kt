package br.com.lojainteligente.venda

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive
import java.math.BigDecimal


data class ItemVendaRequest(

    @field:Positive(message = "O ID do produto deve ser válido")
    val produtoId: Long,

    @field:Positive(message = "A quantidade deve ser maior que zero")
    @field:Max(
        value = 10000,
        message = "A quantidade máxima é 10.000 unidades"
    )
    val quantidade: Int,

    @field:DecimalMin(
        value = "0.00",
        inclusive = true,
        message = "O desconto não pode ser negativo"
    )
    @field:Digits(
        integer = 8,
        fraction = 2,
        message = "O desconto deve ter no máximo duas casas decimais"
    )
    val desconto: BigDecimal = BigDecimal.ZERO



)