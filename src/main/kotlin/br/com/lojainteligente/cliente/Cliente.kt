
package br.com.lojainteligente.cliente

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "clientes")
class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var nome: String = "",

    @Column(nullable = false, unique = true, length = 150)
    var email: String = "",

    @Column(length = 20)
    var telefone: String? = null,

    @Column(nullable = false)
    var ativo: Boolean = true,

    @Column(nullable = false)
    var dataCadastro: LocalDateTime = LocalDateTime.now()
)
