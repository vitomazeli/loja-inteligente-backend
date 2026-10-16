package br.com.lojainteligente.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(

    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.expiration}")
    private val expiration: Long

) {

    private fun getKey(): SecretKey =
        Keys.hmacShaKeyFor(
            secret.toByteArray(StandardCharsets.UTF_8)
        )

    fun gerarToken(email: String): String =
        Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getKey())
            .compact()

    private fun extrairClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload

    fun extrairEmail(token: String): String =
        extrairClaims(token).subject

    fun tokenValido(token: String): Boolean =
        try {
            val claims = extrairClaims(token)

            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
}