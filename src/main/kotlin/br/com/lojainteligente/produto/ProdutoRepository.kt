package br.com.lojainteligente.produto

import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository : JpaRepository<Produto, Long> {

    fun existsByCodigoBarras(codigoBarras: String): Boolean

    fun existsByCodigoBarrasAndIdNot(
        codigoBarras: String,
        id: Long
    ): Boolean
}