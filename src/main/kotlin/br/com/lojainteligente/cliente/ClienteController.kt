
package br.com.lojainteligente.cliente

import br.com.lojainteligente.cliente.dto.ClienteRequest
import br.com.lojainteligente.cliente.dto.ClienteResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clientes")
class ClienteController(
    private val clienteService: ClienteService
) {

    @PostMapping
    fun criar(
        @Valid @RequestBody request: ClienteRequest
    ): ResponseEntity<ClienteResponse> {
        val cliente = clienteService.criar(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cliente)
    }

    @GetMapping
    fun listar(): List<ClienteResponse> {
        return clienteService.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(
        @PathVariable id: Long
    ): ClienteResponse {
        return clienteService.buscarPorId(id)
    }

    @GetMapping("/buscar")
    fun buscarPorNome(
        @RequestParam nome: String
    ): List<ClienteResponse> {
        return clienteService.buscarPorNome(nome)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @Valid @RequestBody request: ClienteRequest
    ): ClienteResponse {
        return clienteService.atualizar(id, request)
    }

    @DeleteMapping("/{id}")
    fun excluir(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        clienteService.excluir(id)

        return ResponseEntity.noContent().build()
    }
}
