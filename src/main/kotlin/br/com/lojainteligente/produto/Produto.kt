
package br.com.lojainteligente.produto

import br.com.lojainteligente.categoria.Categoria
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "produtos")
class Produto(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var nome: String = "",

    @Column(length = 255)
    var descricao: String? = null,

    @Column(
        name = "codigo_barras",
        nullable = false,
        unique = true,
        length = 50
    )
    var codigoBarras: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var preco: BigDecimal = BigDecimal.ZERO,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    var categoria: Categoria? = null,

    @Column(nullable = false)
    var ativo: Boolean = true,

    @Column(nullable = false)
    var dataCadastro: LocalDateTime = LocalDateTime.now()
)
