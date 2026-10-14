
package br.com.lojainteligente.cliente

import br.com.lojainteligente.cliente.dto.ClienteRequest
import br.com.lojainteligente.cliente.dto.ClienteResponse
import org.springframework.stereotype.Component

@Component
class ClienteMapper {

    fun toEntity(request: ClienteRequest): Cliente {
        return Cliente(
            nome = request.nome.trim(),
            email = request.email.trim(),
            telefone = request.telefone?.trim()?.takeIf { it.isNotEmpty() },
            ativo = request.ativo
        )
    }

    fun atualizar(
        cliente: Cliente,
        request: ClienteRequest
    ): Cliente {
        cliente.nome = request.nome.trim()
        cliente.email = request.email.trim()
        cliente.telefone =
            request.telefone?.trim()?.takeIf { it.isNotEmpty() }
        cliente.ativo = request.ativo

        return cliente
    }

    fun toResponse(cliente: Cliente): ClienteResponse {
        return ClienteResponse(
            id = cliente.id,
            nome = cliente.nome,
            email = cliente.email,
            telefone = cliente.telefone,
            ativo = cliente.ativo,
            dataCadastro = cliente.dataCadastro
        )
    }
}
