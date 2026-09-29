package br.com.lojainteligente.cliente

import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository : JpaRepository<Cliente, Long> {

    fun existsByEmail(email: String): Boolean

    fun existsByEmailAndIdNot(
        email: String,
        id: Long
    ): Boolean
}