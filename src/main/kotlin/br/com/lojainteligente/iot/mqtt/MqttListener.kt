
package br.com.lojainteligente.iot.mqtt

import org.springframework.stereotype.Component
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import tools.jackson.databind.ObjectMapper
import br.com.lojainteligente.iot.dto.LeituraIoTMessage
import br.com.lojainteligente.iot.service.LeituraIoTService
import jakarta.annotation.PostConstruct
import java.util.concurrent.Executors
import jakarta.annotation.PreDestroy

@Component
class MqttListener(
    private val objectMapper: ObjectMapper,
    private val leituraIoTService: LeituraIoTService

) : MqttCallback {
    private val executor = Executors.newSingleThreadExecutor()
    private val mqttServer = "broker.hivemq.com"
    private val mqttPort = 1883
    private val mqttTopic = "kora/prateleira"
    @Volatile
    private var encerrando = false

    private val mqttClient = MqttClient(
        "tcp://$mqttServer:$mqttPort",
        "KoraBackend",
        MemoryPersistence()
    )

    @PostConstruct
    fun conectar() {
        println("Iniciando conexão MQTT...")

        conectarAoBroker()

            println("Conectado ao HiveMQ!")
            println("Inscrito no tópico: $mqttTopic")

    }

    fun conectarAoBroker() {
        if (!mqttClient.isConnected) {
            mqttClient.connect()
            mqttClient.setCallback(this)
            mqttClient.subscribe(mqttTopic)
        }
    }

    @PreDestroy
    fun encerrar() {
            encerrando = true
            executor.shutdownNow()

        if (mqttClient.isConnected) {
            mqttClient.disconnect()
            mqttClient.close()
        }
            println("Executor MQTT encerrado!")

    }



    override fun messageArrived(
        topic: String?,
        message: MqttMessage?
    ) {
        val mensagem = message?.payload?.toString(Charsets.UTF_8)
            ?: return

        try {
            val leitura = objectMapper.readValue(
                mensagem,
                LeituraIoTMessage::class.java
            )

            require(leitura.distancia >= 0) {
                "A distância não pode ser negativa"
            }

            require(leitura.ocupacao in 0.0..100.0) {
                "A ocupação deve estar entre 0 e 100"
            }

            leituraIoTService.registrarLeitura(
                leitura.distancia,
                leitura.ocupacao
            )

            println("Distância: ${leitura.distancia}")

        } catch (e: Exception) {
            println("Erro ao processar mensagem: ${e.message}")
        }
    }

    override fun connectionLost(cause: Throwable?) {
        println("Conexão MQTT perdida: ${cause?.message}")
        executor.submit {
            while (!mqttClient.isConnected &&
                   !encerrando &&
                   !Thread.currentThread().isInterrupted) {
                try {
                    println("Tentando reconectar ao HiveMQ...")
                    conectarAoBroker()
                    println("Reconectado ao HiveMQ!")

                } catch (e: Exception) {
                    println("Falha na reconexão: ${e.message}")

                    try {
                        Thread.sleep(5000)
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                        encerrando = true
                    }
                }

            }
        }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        println("Entrega MQTT concluída")
    }



}


