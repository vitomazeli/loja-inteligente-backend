package br.com.lojainteligente.venda

import br.com.lojainteligente.venda.dto.VendaRequest
import br.com.lojainteligente.venda.dto.VendaResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vendas")
class VendaController(
    private val service: VendaService
) {

    @PostMapping
    fun criar(
        @Valid @RequestBody request: VendaRequest
    ): ResponseEntity<VendaResponse> {

        val venda = service.criar(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(venda)
    }

    @GetMapping
    fun listar(): List<VendaResponse> {
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscar(
        @PathVariable id: Long
    ): VendaResponse {
        return service.buscarPorId(id)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @Valid @RequestBody request: VendaRequest
    ): VendaResponse {

        return service.atualizar(id, request)
    }

    @DeleteMapping("/{id}")
    fun excluir(
        @PathVariable id: Long
    ): ResponseEntity<Void> {

        service.excluir(id)

        return ResponseEntity.noContent().build()
    }
}