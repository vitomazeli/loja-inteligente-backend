package br.com.lojainteligente.venda

import org.springframework.data.jpa.repository.JpaRepository

interface VendaRepository : JpaRepository<Venda, Long>