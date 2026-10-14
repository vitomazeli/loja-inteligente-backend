
package br.com.lojainteligente.categoria

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "categorias")
class Categoria(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var nome: String = "",

    @Column(length = 255)
    var descricao: String? = null,

    @Column(nullable = false)
    var ativo: Boolean = true,

    @Column(nullable = false)
    var dataCadastro: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var ordemExibicao: Int = 0
)
