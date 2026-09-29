package br.com.lojainteligente.cliente

import org.springframework.data.jpa.repository.JpaRepository

<<<<<<< HEAD
interface ClienteRepository : JpaRepository<Cliente, Long>
=======
interface ClienteRepository : JpaRepository<Cliente, Long> {

    fun existsByEmail(email: String): Boolean

    fun existsByEmailAndIdNot(
        email: String,
        id: Long
    ): Boolean
}
>>>>>>> master
