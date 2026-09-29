package br.com.lojainteligente.cliente

import br.com.lojainteligente.cliente.dto.ClienteRequest
import br.com.lojainteligente.cliente.dto.ClienteResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ClienteService(
    private val repository: ClienteRepository
) {

    fun criar(request: ClienteRequest): ClienteResponse {

        if (repository.existsByEmail(request.email)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe um cliente com esse e-mail"
            )
        }

        val cliente = Cliente(
            nome = request.nome,
            email = request.email
        )

        val clienteSalvo = repository.save(cliente)

        return converterParaResponse(clienteSalvo)
    }

    fun listar(): List<ClienteResponse> {
        return repository.findAll()
            .map { converterParaResponse(it) }
    }

    fun buscarPorId(id: Long): ClienteResponse {

        val cliente = repository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado"
                )
            }

        return converterParaResponse(cliente)
    }

    fun atualizar(
        id: Long,
        request: ClienteRequest
    ): ClienteResponse {

        val cliente = repository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado"
                )
            }

        if (repository.existsByEmailAndIdNot(request.email, id)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe outro cliente com esse e-mail"
            )
        }

        cliente.nome = request.nome
        cliente.email = request.email

        val clienteAtualizado = repository.save(cliente)

        return converterParaResponse(clienteAtualizado)
    }

    fun excluir(id: Long) {

        if (!repository.existsById(id)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            )
        }

        repository.deleteById(id)
    }

    private fun converterParaResponse(
        cliente: Cliente
    ): ClienteResponse {

        return ClienteResponse(
            id = cliente.id,
            nome = cliente.nome,
            email = cliente.email
        )
    }
}