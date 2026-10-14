
package br.com.lojainteligente.categoria

import br.com.lojainteligente.categoria.dto.CategoriaRequest
import br.com.lojainteligente.categoria.dto.CategoriaResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CategoriaService(
    private val categoriaRepository: CategoriaRepository,
    private val categoriaMapper: CategoriaMapper
) {

    fun criar(request: CategoriaRequest): CategoriaResponse {
        val categoria = categoriaMapper.paraEntidade(request)
        val categoriaSalva = categoriaRepository.save(categoria)

        return categoriaMapper.paraResponse(categoriaSalva)
    }

    fun listar(): List<CategoriaResponse> {
        return categoriaRepository.findAll()
            .map { categoriaMapper.paraResponse(it) }
    }

    fun buscarPorId(id: Long): CategoriaResponse {
        val categoria = categoriaRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
                )
            }

        return categoriaMapper.paraResponse(categoria)
    }

    fun atualizar(
        id: Long,
        request: CategoriaRequest
    ): CategoriaResponse {
        val categoria = categoriaRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
                )
            }

        categoria.nome = request.nome
        categoria.descricao = request.descricao
        categoria.ativo = request.ativo
        categoria.ordemExibicao = request.ordemExibicao

        val categoriaAtualizada = categoriaRepository.save(categoria)

        return categoriaMapper.paraResponse(categoriaAtualizada)
    }

    fun excluir(id: Long) {
        if (!categoriaRepository.existsById(id)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Categoria não encontrada"
            )
        }

        categoriaRepository.deleteById(id)
    }
}
