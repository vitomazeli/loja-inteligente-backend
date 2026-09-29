package br.com.lojainteligente.venda

import br.com.lojainteligente.cliente.Cliente
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "vendas")
class Venda(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    var cliente: Cliente? = null,

    @Column(name = "data_venda", nullable = false)
    var dataVenda: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, precision = 12, scale = 2)
    var total: BigDecimal = BigDecimal.ZERO,

    @OneToMany(
        mappedBy = "venda",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var itens: MutableList<ItemVenda> = mutableListOf()
)