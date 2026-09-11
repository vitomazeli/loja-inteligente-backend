package br.com.lojainteligente.categoria

import jakarta.persistence.*

@Entity
@Table(name = "categorias")
class Categoria(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String = "",

    var descricao: String? = null,

    @Column(nullable = false)
    var ativo: Boolean = true
)