package br.com.lojainteligente.repository

import br.com.lojainteligente.entity.Cliente
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository : JpaRepository<Cliente, Long>