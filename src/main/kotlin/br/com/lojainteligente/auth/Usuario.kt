
package br.com.lojainteligente.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "usuarios")
class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var senha: String,

    @Column(nullable = false)
    var perfil: String = "VENDEDOR",

    @Column(nullable = false)
    var ativo: Boolean = true
)
