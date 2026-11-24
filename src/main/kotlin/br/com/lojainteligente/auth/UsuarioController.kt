
package br.com.lojainteligente.auth

import br.com.lojainteligente.auth.dto.AtualizarUsuarioRequest
import br.com.lojainteligente.auth.dto.CadastroResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

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

    @GetMapping("/{id}")
    fun buscarPorId(
        @PathVariable id: Long
    ): ResponseEntity<CadastroResponse> {

        val usuario = usuarioRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado."
                )
            }
        val response = CadastroResponse(
            id = usuario.id,
            nome = usuario.nome,
            email = usuario.email,
            perfil = usuario.perfil,
            ativo = usuario.ativo
        )
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")

    fun atualizar(
        @PathVariable id: Long,
        @RequestBody request: AtualizarUsuarioRequest
    ): ResponseEntity<CadastroResponse> {

        val usuario = usuarioRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado."
                )
            }

        val totalAdminsAtivos =
            usuarioRepository.countByPerfilAndAtivo("ADMIN", true)
        if (totalAdminsAtivos == 1L) {

            if (
                usuario.perfil == "ADMIN" &&
                usuario.ativo &&
                (request.perfil != "ADMIN" || !request.ativo)
            ) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível desativar ou rebaixar o último administrador ativo."
                )
            }
        }



        if (request.perfil !in listOf("ADMIN", "VENDEDOR")) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Perfil inválido."
            )
        }

        if (
            usuarioRepository.existsByEmailAndIdNot(
                request.email.trim().lowercase(),
                id
            )
        ) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Este e-mail já está cadastrado."
            )
        }

        usuario.nome = request.nome.trim()
        usuario.email = request.email.trim().lowercase()
        usuario.perfil = request.perfil
        usuario.ativo = request.ativo

        val usuarioAtualizado = usuarioRepository.save(usuario)


        val response = CadastroResponse(
            id = usuarioAtualizado.id,
            nome = usuarioAtualizado.nome,
            email = usuarioAtualizado.email,
            perfil = usuarioAtualizado.perfil,
            ativo = usuarioAtualizado.ativo
        )

        return ResponseEntity.ok(response)


    }
}
