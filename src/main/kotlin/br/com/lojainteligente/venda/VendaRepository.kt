
package br.com.lojainteligente.venda

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VendaRepository : JpaRepository<Venda, Long> {

    @Query(
        "SELECT v FROM Venda v " +
                "WHERE LOWER(v.status) LIKE LOWER(CONCAT('%', :status, '%'))"
    )
    fun buscarPorStatus(@Param("status") status: String): List<Venda>
}
