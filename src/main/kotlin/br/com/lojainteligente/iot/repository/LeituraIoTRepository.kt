package br.com.lojainteligente.iot.repository

import br.com.lojainteligente.iot.entity.LeituraIoT
import org.springframework.data.jpa.repository.JpaRepository

interface LeituraIoTRepository : JpaRepository<LeituraIoT, Long>