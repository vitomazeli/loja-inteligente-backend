
package br.com.lojainteligente.cliente.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ClienteRequest(

    @field:NotBlank(message = "O nome é obrigatório")
    @field:Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    val nome: String,

    @field:NotBlank(message = "O email é obrigatório")
    @field:Email(message = "Informe um email válido")
    @field:Size(max = 150, message = "O email deve ter no máximo 150 caracteres")
    val email: String,

    @field:Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    val telefone: String? = null,

    val ativo: Boolean = true
)
