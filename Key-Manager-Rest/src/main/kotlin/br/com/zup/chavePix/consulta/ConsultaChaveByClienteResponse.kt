package br.com.zup.chavePix.consulta

import br.com.zup.ConsultaByClienteResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ConsultaChaveByClienteResponse(
    chavePix: ConsultaByClienteResponse.ChavePix
){
    val pixId = chavePix.pixId
    val tipoChave = chavePix.tipoChave
    val chave = chavePix.chave
    val tipoConta = chavePix.tipoConta
    val criadoEm = chavePix.criadoEm.let{
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds,it.nanos.toLong()),ZoneOffset.UTC)
    }
}
