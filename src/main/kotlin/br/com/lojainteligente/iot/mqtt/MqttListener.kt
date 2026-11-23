
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

@Component
class MqttListener(
    private val objectMapper: ObjectMapper,
    private val leituraIoTService: LeituraIoTService
) : MqttCallback {

    private val mqttServer = "broker.hivemq.com"
    private val mqttPort = 1883
    private val mqttTopic = "kora/prateleira"

    private val mqttClient = MqttClient(
        "tcp://$mqttServer:$mqttPort",
        "KoraBackend",
        MemoryPersistence()
    )

    @PostConstruct
    fun conectar() {
        println("Iniciando conexão MQTT...")
        if (!mqttClient.isConnected) {
            mqttClient.connect()
            mqttClient.setCallback(this)
            mqttClient.subscribe(mqttTopic)

            println("Conectado ao HiveMQ!")
            println("Inscrito no tópico: $mqttTopic")
        }
    }

    override fun messageArrived(
        topic: String?,
        message: MqttMessage?
    ) {
        val mensagem = message?.payload?.toString(Charsets.UTF_8)
            ?: return

        val leitura = objectMapper.readValue(
            mensagem,
            LeituraIoTMessage::class.java
        )

        leituraIoTService.registrarLeitura(
            leitura.distancia,
            leitura.ocupacao
        )
    }

    override fun connectionLost(cause: Throwable?) {
        println("Conexão MQTT perdida: ${cause?.message}")
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        println("Entrega MQTT concluída")
    }
}


