
package br.com.lojainteligente.venda

import br.com.lojainteligente.cliente.ClienteRepository
import br.com.lojainteligente.produto.ProdutoRepository
import br.com.lojainteligente.venda.dto.VendaRequest
import br.com.lojainteligente.venda.dto.VendaResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@Service
class VendaService(
    private val vendaRepository: VendaRepository,
    private val clienteRepository: ClienteRepository,
    private val produtoRepository: ProdutoRepository,
    private val vendaMapper: VendaMapper
) {

    @Transactional
    fun criar(request: VendaRequest): VendaResponse {
        val cliente = clienteRepository.findById(request.clienteId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado"
                )
            }

        val venda = vendaMapper.paraEntidade(request, cliente)

        adicionarItens(venda, request)
        recalcularTotal(venda)

        val vendaSalva = vendaRepository.save(venda)

        return vendaMapper.paraResponse(vendaSalva)
    }

    @Transactional(readOnly = true)
    fun listar(): List<VendaResponse> {
        return vendaRepository.findAll()
            .map { vendaMapper.paraResponse(it) }
    }


    @Transactional(readOnly = true)
    fun buscarPorStatus(status: String): List<VendaResponse> {
        return vendaRepository.buscarPorStatus(status)
            .map { vendaMapper.paraResponse(it) }
    }


    @Transactional(readOnly = true)
    fun buscarPorId(id: Long): VendaResponse {
        val venda = buscarVenda(id)
        return vendaMapper.paraResponse(venda)
    }

    @Transactional
    fun atualizar(
        id: Long,
        request: VendaRequest
    ): VendaResponse {
        val venda = buscarVenda(id)

        val cliente = clienteRepository.findById(request.clienteId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado"
                )
            }

        venda.cliente = cliente
        venda.observacao =
            request.observacao?.trim()?.takeIf { it.isNotEmpty() }

        venda.itens.clear()

        adicionarItens(venda, request)
        recalcularTotal(venda)

        val vendaAtualizada = vendaRepository.save(venda)

        return vendaMapper.paraResponse(vendaAtualizada)
    }

    @Transactional
    fun excluir(id: Long) {
        val venda = buscarVenda(id)
        vendaRepository.delete(venda)
    }

    private fun adicionarItens(
        venda: Venda,
        request: VendaRequest
    ) {
        request.itens.forEach { itemRequest ->
            val produto = produtoRepository.findById(itemRequest.produtoId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto ${itemRequest.produtoId} não encontrado"
                    )
                }

            val item = vendaMapper.paraItemEntidade(
                venda = venda,
                produto = produto,
                request = itemRequest
            )

            venda.itens.add(item)
        }
    }

    private fun recalcularTotal(venda: Venda) {
        venda.total = venda.itens.fold(BigDecimal.ZERO) { acumulado, item ->
            acumulado.add(item.subtotal.subtract(item.desconto))
        }
    }

    private fun buscarVenda(id: Long): Venda {
        return vendaRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Venda não encontrada"
                )
            }
    }
}
