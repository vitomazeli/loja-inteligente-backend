
package br.com.lojainteligente.venda

import br.com.lojainteligente.cliente.Cliente
import br.com.lojainteligente.produto.Produto
import br.com.lojainteligente.venda.dto.ItemVendaResponse
import br.com.lojainteligente.venda.dto.VendaRequest
import br.com.lojainteligente.venda.dto.VendaResponse
import org.springframework.stereotype.Component
import java.math.BigDecimal
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@Component
class VendaMapper {

    fun paraEntidade(
        request: VendaRequest,
        cliente: Cliente
    ): Venda {
        return Venda(
            cliente = cliente,
            observacao = request.observacao?.trim()?.takeIf { it.isNotEmpty() }
        )
    }

    fun paraItemEntidade(
        venda: Venda,
        produto: Produto,
        request: ItemVendaRequest
    ): ItemVenda {
        val subtotal = produto.preco.multiply(
            BigDecimal.valueOf(request.quantidade.toLong())
        )

        if (request.desconto > subtotal) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O desconto não pode ser maior que o subtotal do item."
            )
        }

        return ItemVenda(
            venda = venda,
            produto = produto,
            quantidade = request.quantidade,
            precoUnitario = produto.preco,
            subtotal = subtotal,
            desconto = request.desconto
        )
    }

    fun paraResponse(venda: Venda): VendaResponse {
        return VendaResponse(
            id = venda.id,
            clienteId = venda.cliente?.id,
            clienteNome = venda.cliente?.nome,
            dataVenda = venda.dataVenda,
            total = venda.total,
            status = venda.status,
            observacao = venda.observacao,
            itens = venda.itens.map { paraItemResponse(it) }
        )
    }

    private fun paraItemResponse(item: ItemVenda): ItemVendaResponse {
        return ItemVendaResponse(
            id = item.id,
            produtoId = item.produto?.id,
            produtoNome = item.produto?.nome,
            quantidade = item.quantidade,
            precoUnitario = item.precoUnitario,
            subtotal = item.subtotal,
            desconto = item.desconto

        )
    }
}
