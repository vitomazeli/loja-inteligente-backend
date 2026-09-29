package br.com.lojainteligente.cliente

import jakarta.persistence.*

@Entity
@Table(name = "clientes")
<<<<<<< HEAD
data class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nome: String,

    val email: String
=======
class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String = "",

    @Column(nullable = false, unique = true)
    var email: String = ""
>>>>>>> master
)