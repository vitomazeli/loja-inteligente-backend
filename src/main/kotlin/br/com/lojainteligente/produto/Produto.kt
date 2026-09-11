package br.com.lojainteligente.produto

import br.com.lojainteligente.categoria.Categoria
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "produtos")
class Produto(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String = "",

    var descricao: String? = null,

    @Column(
        name = "codigo_barras",
        nullable = false,
        unique = true
    )
    var codigoBarras: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var preco: BigDecimal = BigDecimal.ZERO,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    var categoria: Categoria? = null,

    @Column(nullable = false)
    var ativo: Boolean = true
)