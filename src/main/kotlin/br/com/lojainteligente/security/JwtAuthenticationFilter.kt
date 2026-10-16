package br.com.lojainteligente.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)

        if (jwtService.tokenValido(token)) {

            val email = jwtService.extrairEmail(token)

            val auth = UsernamePasswordAuthenticationToken(
                email,
                null,
                emptyList()
            )

            auth.details =
                WebAuthenticationDetailsSource()
                    .buildDetails(request)

            SecurityContextHolder
                .getContext()
                .authentication = auth
        }

        filterChain.doFilter(request, response)
    }
}