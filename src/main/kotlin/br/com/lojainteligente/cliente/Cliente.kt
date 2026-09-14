package br.com.lojainteligente.cliente

import jakarta.persistence.*

@Entity
@Table(name = "clientes")
data class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nome: String,

    val email: String
)