package br.com.lojainteligente.venda

import br.com.lojainteligente.produto.Produto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "itens_venda")
class ItemVenda(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    var venda: Venda? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    var produto: Produto? = null,

    @Column(nullable = false)
    var quantidade: Int = 1,

    @Column(
        name = "preco_unitario",
        nullable = false,
        precision = 10,
        scale = 2
    )
    var precoUnitario: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, precision = 12, scale = 2)
    var subtotal: BigDecimal = BigDecimal.ZERO
)