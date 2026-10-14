
package br.com.lojainteligente.categoria

import br.com.lojainteligente.categoria.dto.CategoriaRequest
import br.com.lojainteligente.categoria.dto.CategoriaResponse
import org.springframework.stereotype.Component

@Component
class CategoriaMapper {

    fun paraEntidade(request: CategoriaRequest): Categoria {
        return Categoria(
            nome = request.nome,
            descricao = request.descricao,
            ativo = request.ativo,
            ordemExibicao = request.ordemExibicao
        )
    }

    fun paraResponse(categoria: Categoria): CategoriaResponse {
        return CategoriaResponse(
            id = categoria.id,
            nome = categoria.nome,
            descricao = categoria.descricao,
            ativo = categoria.ativo,
            dataCadastro = categoria.dataCadastro,
            ordemExibicao = categoria.ordemExibicao
        )
    }
}
