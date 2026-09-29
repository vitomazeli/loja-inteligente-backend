package br.com.lojainteligente.categoria

import br.com.lojainteligente.categoria.dto.CategoriaRequest
import br.com.lojainteligente.categoria.dto.CategoriaResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CategoriaService(
    private val categoriaRepository: CategoriaRepository
) {

    fun criar(request: CategoriaRequest): CategoriaResponse {

        val categoria = Categoria(
            nome = request.nome,
            descricao = request.descricao,
            ativo = request.ativo
        )

        val categoriaSalva = categoriaRepository.save(categoria)

        return converterParaResponse(categoriaSalva)
    }

    fun listar(): List<CategoriaResponse> {
        return categoriaRepository.findAll()
            .map { converterParaResponse(it) }
    }

    fun buscarPorId(id: Long): CategoriaResponse {

        val categoria = categoriaRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoria não encontrada"
                )
            }

        return converterParaResponse(categoria)
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

        val categoriaAtualizada = categoriaRepository.save(categoria)

        return converterParaResponse(categoriaAtualizada)
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

    private fun converterParaResponse(
        categoria: Categoria
    ): CategoriaResponse {

        return CategoriaResponse(
            id = categoria.id,
            nome = categoria.nome,
            descricao = categoria.descricao,
            ativo = categoria.ativo
        )
    }
}