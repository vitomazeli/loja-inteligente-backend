package br.com.lojainteligente.venda

import br.com.lojainteligente.cliente.ClienteRepository
import br.com.lojainteligente.produto.ProdutoRepository
import br.com.lojainteligente.venda.dto.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@Service
class VendaService(
    private val vendaRepository: VendaRepository,
    private val clienteRepository: ClienteRepository,
    private val produtoRepository: ProdutoRepository
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

        val venda = Venda(
            cliente = cliente
        )

        var totalVenda = BigDecimal.ZERO

        request.itens.forEach { itemRequest ->

            val produto = produtoRepository.findById(itemRequest.produtoId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto ${itemRequest.produtoId} não encontrado"
                    )
                }

            val subtotal = produto.preco.multiply(
                BigDecimal.valueOf(itemRequest.quantidade.toLong())
            )

            val item = ItemVenda(
                venda = venda,
                produto = produto,
                quantidade = itemRequest.quantidade,
                precoUnitario = produto.preco,
                subtotal = subtotal
            )

            venda.itens.add(item)

            totalVenda = totalVenda.add(subtotal)
        }

        venda.total = totalVenda

        val vendaSalva = vendaRepository.save(venda)

        return converterParaResponse(vendaSalva)
    }

    @Transactional(readOnly = true)
    fun listar(): List<VendaResponse> {
        return vendaRepository.findAll()
            .map { converterParaResponse(it) }
    }

    @Transactional(readOnly = true)
    fun buscarPorId(id: Long): VendaResponse {

        val venda = buscarVenda(id)

        return converterParaResponse(venda)
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

        venda.itens.clear()

        var totalVenda = BigDecimal.ZERO

        request.itens.forEach { itemRequest ->

            val produto = produtoRepository.findById(itemRequest.produtoId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto ${itemRequest.produtoId} não encontrado"
                    )
                }

            val subtotal = produto.preco.multiply(
                BigDecimal.valueOf(itemRequest.quantidade.toLong())
            )

            val item = ItemVenda(
                venda = venda,
                produto = produto,
                quantidade = itemRequest.quantidade,
                precoUnitario = produto.preco,
                subtotal = subtotal
            )

            venda.itens.add(item)

            totalVenda = totalVenda.add(subtotal)
        }

        venda.total = totalVenda

        val vendaAtualizada = vendaRepository.save(venda)

        return converterParaResponse(vendaAtualizada)
    }

    @Transactional
    fun excluir(id: Long) {

        val venda = buscarVenda(id)

        vendaRepository.delete(venda)
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

    private fun converterParaResponse(
        venda: Venda
    ): VendaResponse {

        return VendaResponse(
            id = venda.id,
            clienteId = venda.cliente?.id,
            clienteNome = venda.cliente?.nome,
            dataVenda = venda.dataVenda,
            total = venda.total,

            itens = venda.itens.map { item ->

                ItemVendaResponse(
                    id = item.id,
                    produtoId = item.produto?.id,
                    produtoNome = item.produto?.nome,
                    quantidade = item.quantidade,
                    precoUnitario = item.precoUnitario,
                    subtotal = item.subtotal
                )
            }
        )
    }
}