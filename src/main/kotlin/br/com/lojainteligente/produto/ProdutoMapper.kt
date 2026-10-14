
package br.com.lojainteligente.produto

import br.com.lojainteligente.categoria.Categoria
import br.com.lojainteligente.produto.dto.ProdutoRequest
import br.com.lojainteligente.produto.dto.ProdutoResponse
import org.springframework.stereotype.Component

@Component
class ProdutoMapper {

    fun paraEntidade(
        request: ProdutoRequest,
        categoria: Categoria
    ): Produto {
        return Produto(
            nome = request.nome,
            descricao = request.descricao,
            codigoBarras = request.codigoBarras,
            preco = request.preco,
            categoria = categoria,
            ativo = request.ativo
        )
    }

    fun paraResponse(produto: Produto): ProdutoResponse {
        return ProdutoResponse(
            id = produto.id,
            nome = produto.nome,
            descricao = produto.descricao,
            codigoBarras = produto.codigoBarras,
            preco = produto.preco,
            categoriaId = produto.categoria?.id,
            categoriaNome = produto.categoria?.nome,
            ativo = produto.ativo,
            dataCadastro = produto.dataCadastro
        )
    }
}
