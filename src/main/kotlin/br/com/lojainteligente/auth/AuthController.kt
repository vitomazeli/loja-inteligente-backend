
package br.com.lojainteligente.auth

import br.com.lojainteligente.auth.dto.CadastroRequest
import br.com.lojainteligente.auth.dto.LoginRequest
import br.com.lojainteligente.auth.dto.LoginResponse
import br.com.lojainteligente.security.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun cadastrar(
        @RequestBody request: CadastroRequest
    ): ResponseEntity<String> {

        if (
            request.nome.isBlank() ||
            request.email.isBlank() ||
            request.senha.isBlank()
        ) {
            return ResponseEntity
                .badRequest()
                .body("Nome, e-mail e senha são obrigatórios.")
        }

        if (request.senha.length < 6) {
            return ResponseEntity
                .badRequest()
                .body("A senha deve ter pelo menos 6 caracteres.")
        }

        val email = request.email.trim().lowercase()

        if (usuarioRepository.findByEmail(email) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Este e-mail já está cadastrado.")
        }



        val senhaCriptografada = requireNotNull(
            passwordEncoder.encode(request.senha)
        ) {
            "Não foi possível criptografar a senha."
        }

        val usuario = Usuario(
            nome = request.nome.trim(),
            email = email,
            senha = senhaCriptografada,
            perfil = "VENDEDOR",
            ativo = true
        )



        usuarioRepository.save(usuario)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Usuário cadastrado com sucesso.")
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {

        val email = request.email.trim().lowercase()

        val usuario = usuarioRepository.findByEmail(email)
            ?: return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build()

        if (!passwordEncoder.matches(request.senha, usuario.senha)) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build()
        }

        if (!usuario.ativo) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build()
        }

        val token = jwtService.gerarToken(usuario.email)

        return ResponseEntity.ok(LoginResponse(token))
    }
}
