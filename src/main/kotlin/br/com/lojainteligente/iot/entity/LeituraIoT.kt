
package br.com.lojainteligente.iot.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "leituras_iot")
class LeituraIoT(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var distancia: Double,

    @Column(nullable = false)
    var ocupacao: Double,


    @Column(nullable = false)
    var dataHora: LocalDateTime = LocalDateTime.now(),


)
