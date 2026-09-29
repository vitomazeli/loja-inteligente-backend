package br.com.lojainteligente.cliente

<<<<<<< HEAD
=======
import br.com.lojainteligente.cliente.dto.ClienteRequest
import br.com.lojainteligente.cliente.dto.ClienteResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
>>>>>>> master
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clientes")
class ClienteController(
    private val service: ClienteService
) {

<<<<<<< HEAD
    @GetMapping
    fun listar(): List<Cliente> {
=======
    @PostMapping
    fun criar(
        @Valid @RequestBody request: ClienteRequest
    ): ResponseEntity<ClienteResponse> {

        val cliente = service.criar(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cliente)
    }

    @GetMapping
    fun listar(): List<ClienteResponse> {
>>>>>>> master
        return service.listar()
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    fun buscar(@PathVariable id: Long): Cliente? {
        return service.buscarPorId(id)
    }

    @PostMapping
    fun criar(@RequestBody cliente: Cliente): Cliente {
        return service.salvar(cliente)
    }

    @DeleteMapping("/{id}")
    fun excluir(@PathVariable id: Long) {
        service.excluir(id)
=======
    fun buscar(
        @PathVariable id: Long
    ): ClienteResponse {
        return service.buscarPorId(id)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @Valid @RequestBody request: ClienteRequest
    ): ClienteResponse {
        return service.atualizar(id, request)
    }

    @DeleteMapping("/{id}")
    fun excluir(
        @PathVariable id: Long
    ): ResponseEntity<Void> {

        service.excluir(id)

        return ResponseEntity.noContent().build()
>>>>>>> master
    }
}