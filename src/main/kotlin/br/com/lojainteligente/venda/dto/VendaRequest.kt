
package br.com.lojainteligente.venda.dto

import br.com.lojainteligente.venda.ItemVendaRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class VendaRequest(

    @field:Positive(message = "O cliente é obrigatório")
    val clienteId: Long,

    @field:NotEmpty(
        message = "A venda deve possuir pelo menos um produto"
    )
    @field:Size(
        max = 100,
        message = "A venda pode possuir no máximo 100 itens"
    )
    @field:Valid
    val itens: List<ItemVendaRequest>,

    @field:Size(
        max = 255,
        message = "A observação deve ter no máximo 255 caracteres"
    )
    val observacao: String? = null
)
