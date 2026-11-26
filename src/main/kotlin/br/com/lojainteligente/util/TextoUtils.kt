package br.com.lojainteligente.util


import java.text.Normalizer

fun normalizarTexto(texto: String): String {
    val textoSemAcentos = Normalizer.normalize(
        texto.trim().lowercase(),
        Normalizer.Form.NFD
    )

    return textoSemAcentos.replace(Regex("\\p{M}+"), "")
}