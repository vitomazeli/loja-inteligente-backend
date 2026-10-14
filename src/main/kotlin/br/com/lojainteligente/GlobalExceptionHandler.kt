
package br.com.lojainteligente

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

data class ErroValidacaoResponse(
    val status: Int,
    val mensagens: List<String>
)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun tratarErroValidacao(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErroValidacaoResponse> {

        val mensagens = ex.bindingResult.fieldErrors
            .map { erro ->
                erro.defaultMessage ?: "Valor inválido"
            }
            .distinct()

        val resposta = ErroValidacaoResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            mensagens = mensagens
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(resposta)
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun tratarErroDeStatus(
        ex: ResponseStatusException
    ): ResponseEntity<ErroValidacaoResponse> {

        val resposta = ErroValidacaoResponse(
            status = ex.statusCode.value(),
            mensagens = listOf(
                ex.reason ?: "Ocorreu um erro na requisição"
            )
        )

        return ResponseEntity
            .status(ex.statusCode)
            .body(resposta)
    }
}
