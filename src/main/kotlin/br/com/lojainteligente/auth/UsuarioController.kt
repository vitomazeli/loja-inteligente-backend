
package br.com.lojainteligente.auth

import br.com.lojainteligente.auth.dto.CadastroResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    private val usuarioRepository: UsuarioRepository
) {

    @GetMapping
    fun listar(): List<CadastroResponse> {
        return usuarioRepository.findAll().map { usuario ->
            CadastroResponse(
                id = usuario.id,
                nome = usuario.nome,
                email = usuario.email,
                perfil = usuario.perfil,
                ativo = usuario.ativo
            )
        }
    }
}
