
package br.com.lojainteligente.cliente

import br.com.lojainteligente.cliente.dto.ClienteRequest
import br.com.lojainteligente.cliente.dto.ClienteResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class ClienteService(
    private val clienteRepository: ClienteRepository,
    private val clienteMapper: ClienteMapper
) {

    @Transactional
    fun criar(request: ClienteRequest): ClienteResponse {
        val cliente = clienteMapper.toEntity(request)
        return clienteMapper.toResponse(
            clienteRepository.save(cliente)
        )
    }

    @Transactional(readOnly = true)
    fun listar(): List<ClienteResponse> {
        return clienteRepository.findAll()
            .map { clienteMapper.toResponse(it) }
    }

    @Transactional(readOnly = true)
    fun buscarPorId(id: Long): ClienteResponse {
        return clienteMapper.toResponse(buscarEntidade(id))
    }

    @Transactional
    fun atualizar(
        id: Long,
        request: ClienteRequest
    ): ClienteResponse {
        val cliente = buscarEntidade(id)
        clienteMapper.atualizar(cliente, request)

        return clienteMapper.toResponse(
            clienteRepository.save(cliente)
        )
    }

    @Transactional
    fun excluir(id: Long) {
        val cliente = buscarEntidade(id)
        clienteRepository.delete(cliente)
    }

    @Transactional(readOnly = true)
    fun buscarPorNome(nome: String): List<ClienteResponse> {
        return clienteRepository.buscarPorNome(nome)
            .map { clienteMapper.toResponse(it) }
    }

    private fun buscarEntidade(id: Long): Cliente {
        return clienteRepository.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            )
        }
    }
}
