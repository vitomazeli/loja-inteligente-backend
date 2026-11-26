
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
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import br.com.lojainteligente.auth.dto.ErroResponse

@RestController
@RequestMapping("/auth")
class AuthController(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping("/register")
    fun cadastrar(
        @RequestBody request: CadastroRequest
    ): ResponseEntity<String> {

        if (
            request.nome.isBlank() ||
            request.email.isBlank() ||
            request.senha.isBlank() ||
            request.confsenha.isBlank()
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

        if (request.senha != request.confsenha) {
            return ResponseEntity
                .badRequest()
                .body("As senhas não coincidem.")
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
    ): ResponseEntity<Any> {

        val email = request.email.trim().lowercase()

        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    email,
                    request.senha
                )
            )

            val token = jwtService.gerarToken(authentication.name)

            ResponseEntity.ok(LoginResponse(token))

        } catch (e: AuthenticationException) {
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErroResponse("E-mail ou senha incorretos."))
        }
    }
}
