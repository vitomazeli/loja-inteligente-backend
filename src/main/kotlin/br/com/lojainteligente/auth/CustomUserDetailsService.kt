
package br.com.lojainteligente.auth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository

) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val usuario = usuarioRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado")

        val autoridade = SimpleGrantedAuthority("ROLE_${usuario.perfil}")

        return User.builder()
            .username(usuario.email)
            .password(usuario.senha)
            .authorities(listOf(autoridade))
            .disabled(!usuario.ativo)
            .build()
    }
}
