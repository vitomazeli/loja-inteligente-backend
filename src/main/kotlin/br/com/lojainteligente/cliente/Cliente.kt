package br.com.lojainteligente.cliente

import jakarta.persistence.*

@Entity
@Table(name = "clientes")
class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String = "",

    @Column(nullable = false, unique = true)
    var email: String = ""
)