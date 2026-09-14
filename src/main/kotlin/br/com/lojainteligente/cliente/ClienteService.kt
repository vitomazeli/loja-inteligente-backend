package br.com.lojainteligente.cliente


import org.springframework.stereotype.Service

@Service
class ClienteService(
    private val repository: ClienteRepository
) {

    fun listar(): List<Cliente> {
        return repository.findAll()
    }

    fun buscarPorId(id: Long): Cliente? {
        return repository.findById(id).orElse(null)
    }

    fun salvar(cliente: Cliente): Cliente {
        return repository.save(cliente)
    }

    fun excluir(id: Long) {
        repository.deleteById(id)
    }
}