package br.com.lojainteligente.produto

import br.com.lojainteligente.categoria.CategoriaRepository
import br.com.lojainteligente.produto.dto.ProdutoRequest
import br.com.lojainteligente.produto.dto.ProdutoResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository,
    private val categoriaRepository: CategoriaRepository
) {

    fun criar(request: ProdutoRequest): ProdutoResponse {

        if (produtoRepository.existsByCodigoBarras(request.codigoBarras)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe um produto com esse código de barras"
            )
        }

        val categoria = categoriaRepository.findById(request.categoriaId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
                )
            }

        val produto = Produto(
            nome = request.nome,
            descricao = request.descricao,
            codigoBarras = request.codigoBarras,
            preco = request.preco,
            categoria = categoria,
            ativo = request.ativo
        )

        val produtoSalvo = produtoRepository.save(produto)

        return converterParaResponse(produtoSalvo)
    }

    fun listar(): List<ProdutoResponse> {
        return produtoRepository.findAll()
            .map { converterParaResponse(it) }
    }

    fun buscarPorId(id: Long): ProdutoResponse {

        val produto = produtoRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Produto não encontrado"
                )
            }

        return converterParaResponse(produto)
    }

    fun atualizar(
        id: Long,
        request: ProdutoRequest
    ): ProdutoResponse {

        val produto = produtoRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Produto não encontrado"
                )
            }

        if (
            produtoRepository.existsByCodigoBarrasAndIdNot(
                request.codigoBarras,
                id
            )
        ) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe outro produto com esse código de barras"
            )
        }

        val categoria = categoriaRepository.findById(request.categoriaId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
                )
            }

        produto.nome = request.nome
        produto.descricao = request.descricao
        produto.codigoBarras = request.codigoBarras
        produto.preco = request.preco
        produto.categoria = categoria
        produto.ativo = request.ativo

        val produtoAtualizado = produtoRepository.save(produto)

        return converterParaResponse(produtoAtualizado)
    }

    fun excluir(id: Long) {

        if (!produtoRepository.existsById(id)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Produto não encontrado"
            )
        }

        produtoRepository.deleteById(id)
    }

    private fun converterParaResponse(
        produto: Produto
    ): ProdutoResponse {

        return ProdutoResponse(
            id = produto.id,
            nome = produto.nome,
            descricao = produto.descricao,
            codigoBarras = produto.codigoBarras,
            preco = produto.preco,
            categoriaId = produto.categoria?.id,
            categoriaNome = produto.categoria?.nome,
            ativo = produto.ativo
        )
    }
}