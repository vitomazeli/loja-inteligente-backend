
package br.com.lojainteligente.iot.service


import br.com.lojainteligente.iot.entity.LeituraIoT
import br.com.lojainteligente.iot.repository.LeituraIoTRepository
import org.springframework.stereotype.Service


@Service
class LeituraIoTService(
    private val leituraIoTRepository: LeituraIoTRepository
)
{

    fun registrarLeitura (distancia: Double, ocupacao: Double): LeituraIoT {
        val leitura = LeituraIoT (
            distancia = distancia,
            ocupacao = ocupacao
        )
        return leituraIoTRepository.save(leitura)
    }
}


