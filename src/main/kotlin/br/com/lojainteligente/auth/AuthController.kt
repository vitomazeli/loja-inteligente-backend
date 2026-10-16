package br.com.lojainteligente.auth

import br.com.lojainteligente.auth.dto.LoginRequest
import br.com.lojainteligente.auth.dto.LoginResponse
import br.com.lojainteligente.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(


    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {

        println("Email recebido: ${request.email}")

        val usuario = usuarioRepository.findByEmail(request.email)

        println("Usuario encontrado: $usuario")

        if (usuario == null) {
            println("USUARIO NAO ENCONTRADO")
            return ResponseEntity.status(401).build()
        }

        println("Senha banco: ${usuario.senha}")
        println("Senha recebida: ${request.senha}")

        val token = jwtService.gerarToken(usuario.email)

        println("Token gerado: $token")

        return ResponseEntity.ok(
            LoginResponse(token)
        )
    }
}

