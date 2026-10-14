
package br.com.lojainteligente.produto

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProdutoRepository : JpaRepository<Produto, Long> {

    fun existsByCodigoBarras(codigoBarras: String): Boolean

    fun existsByCodigoBarrasAndIdNot(
        codigoBarras: String,
        id: Long
    ): Boolean

    @Query(
        "SELECT p FROM Produto p " +
                "WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))"
    )
    fun buscarPorNome(@Param("nome") nome: String): List<Produto>
}
