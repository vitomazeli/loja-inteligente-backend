package br.com.lojainteligente.cliente

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clientes")
class ClienteController(
    private val service: ClienteService
) {

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
