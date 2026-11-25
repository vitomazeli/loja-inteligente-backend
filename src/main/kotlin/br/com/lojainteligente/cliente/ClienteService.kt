package br.com.lojainteligente.cliente

import br.com.lojainteligente.cliente.dto.ClienteRequest
import br.com.lojainteligente.cliente.dto.ClienteResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import br.com.lojainteligente.venda.VendaRepository

@Service
class ClienteService(
    private val clienteRepository: ClienteRepository,
    private val clienteMapper: ClienteMapper,
    private val vendaRepository: VendaRepository
) {

    @Transactional
    fun criar(request: ClienteRequest): ClienteResponse {
        val cliente = clienteMapper.toEntity(request)

        if (clienteRepository.existsByEmail(cliente.email)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe um cliente cadastrado com este email"
            )
        }

        return clienteMapper.toResponse(
            clienteRepository.save(cliente)
        )
    }

    @Transactional(readOnly = true)
    fun listar(): List<ClienteResponse> =
        clienteRepository.findAll().map {
            clienteMapper.toResponse(it)
        }

    @Transactional(readOnly = true)
    fun buscarPorId(id: Long): ClienteResponse =
        clienteMapper.toResponse(buscarEntidade(id))

    @Transactional
    fun atualizar(
        id: Long,
        request: ClienteRequest
    ): ClienteResponse {
        val cliente = buscarEntidade(id)

        if (clienteRepository.existsByEmailAndIdNot(
                request.email.trim().lowercase(),
                id
            )
        ) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Já existe um cliente cadastrado com este email"
            )
        }

        clienteMapper.atualizar(cliente, request)

        return clienteMapper.toResponse(
            clienteRepository.save(cliente)
        )
    }

    @Transactional
    fun excluir(id: Long) {
        val cliente = buscarEntidade(id)

        if (vendaRepository.existsByClienteId(id)) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Não é possível excluir um cliente que possui vendas vinculadas."
            )
        }

        clienteRepository.delete(cliente)
    }

    @Transactional(readOnly = true)
    fun buscarPorNome(nome: String): List<ClienteResponse> =
        clienteRepository.buscarPorNome(nome).map {
            clienteMapper.toResponse(it)
        }

    private fun buscarEntidade(id: Long): Cliente =
        clienteRepository.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            )
        }
}