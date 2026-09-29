package br.com.lojainteligente.categoria

import br.com.lojainteligente.categoria.dto.CategoriaRequest
import br.com.lojainteligente.categoria.dto.CategoriaResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categorias")
class CategoriaController(
    private val categoriaService: CategoriaService
) {

    @PostMapping
    fun criar(
        @Valid @RequestBody request: CategoriaRequest
    ): ResponseEntity<CategoriaResponse> {

        val categoria = categoriaService.criar(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(categoria)
    }

    @GetMapping
    fun listar(): List<CategoriaResponse> {
        return categoriaService.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(
        @PathVariable id: Long
    ): CategoriaResponse {

        return categoriaService.buscarPorId(id)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @Valid @RequestBody request: CategoriaRequest
    ): CategoriaResponse {

        return categoriaService.atualizar(id, request)
    }

    @DeleteMapping("/{id}")
    fun excluir(
        @PathVariable id: Long
    ): ResponseEntity<Void> {

        categoriaService.excluir(id)

        return ResponseEntity.noContent().build()
    }
}