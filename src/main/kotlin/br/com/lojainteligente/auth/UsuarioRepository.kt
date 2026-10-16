package br.com.lojainteligente.auth

import org.springframework.data.jpa.repository.JpaRepository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByEmail(email: String): Usuario?

}