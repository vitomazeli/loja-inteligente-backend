package br.com.lojainteligente.controller

import br.com.lojainteligente.entity.Cliente
import br.com.lojainteligente.service.ClienteService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clientes")
class ClienteController(
    private val service: ClienteService
) {

    @GetMapping
    fun listar(): List<Cliente> {
        return service.listar()
    }

    @GetMapping("/{id}")
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
    }
}