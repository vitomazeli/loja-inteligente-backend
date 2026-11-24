package br.com.lojainteligente.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import br.com.lojainteligente.auth.CustomUserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val customUserDetailsService: CustomUserDetailsService
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

            val usuario = try {
                customUserDetailsService.loadUserByUsername(email)
            } catch (e: UsernameNotFoundException) {
                SecurityContextHolder.clearContext()
                filterChain.doFilter(request, response)
                return
            }

            if (!usuario.isEnabled) {
                SecurityContextHolder.clearContext()
                filterChain.doFilter(request, response)
                return
            }

            val auth = UsernamePasswordAuthenticationToken(
                usuario,
                null,
                usuario.authorities
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