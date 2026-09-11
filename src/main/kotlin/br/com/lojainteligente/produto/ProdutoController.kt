package br.com.lojainteligente.produto

import br.com.lojainteligente.produto.dto.ProdutoRequest
import br.com.lojainteligente.produto.dto.ProdutoResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/produtos")
class ProdutoController(
    private val produtoService: ProdutoService
) {

    @PostMapping
    fun criar(
        @Valid @RequestBody request: ProdutoRequest
    ): ResponseEntity<ProdutoResponse> {

        val produto = produtoService.criar(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(produto)
    }

    @GetMapping
    fun listar(): List<ProdutoResponse> {
        return produtoService.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(
        @PathVariable id: Long
    ): ProdutoResponse {

        return produtoService.buscarPorId(id)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @Valid @RequestBody request: ProdutoRequest
    ): ProdutoResponse {

        return produtoService.atualizar(id, request)
    }

    @DeleteMapping("/{id}")
    fun excluir(
        @PathVariable id: Long
    ): ResponseEntity<Void> {

        produtoService.excluir(id)

        return ResponseEntity.noContent().build()
    }
}