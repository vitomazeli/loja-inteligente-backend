
package br.com.lojainteligente.categoria

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CategoriaRepository : JpaRepository<Categoria, Long> {

    @Query(
        "SELECT c FROM Categoria c " +
                "WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))"
    )
    fun buscarPorNome(@Param("nome") nome: String): List<Categoria>
}
