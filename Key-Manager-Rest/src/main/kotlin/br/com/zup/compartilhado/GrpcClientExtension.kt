package br.com.zup.compartilhado

import br.com.zup.ConsultaChaveRespose
import br.com.zup.NovaChaveResponse
import br.com.zup.chavePix.cadastro.NovaChavePixResponse
import br.com.zup.chavePix.consulta.ChavePixResponse
import br.com.zup.chavePix.consulta.ConsultaChavePixResponse
import br.com.zup.chavePix.consulta.ContaInfoResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun NovaChaveResponse.toModel(): NovaChavePixResponse{
    return NovaChavePixResponse(clienteId,pixId)
}

fun ConsultaChaveRespose.toModel(): ConsultaChavePixResponse {
    return ConsultaChavePixResponse(
        clienteId = clienteId,
        chavePixId = chavePixId,
        chavePix = ChavePixResponse(
            tipoChave = chavePix.tipoChave.toString(),
            chave = chavePix.chave,
            conta = ContaInfoResponse(
                tipoConta = chavePix.conta.tipoConta.toString(),
                instituicao = chavePix.conta.instituicao,
                nomeTitular = chavePix.conta.nomeTitular,
                cpfTitular = chavePix.conta.cpfTitular,
                agencia = chavePix.conta.agencia,
                numero = chavePix.conta.number
            ),
            criadoEm = chavePix.criadoEm.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds,it.nanos.toLong()),ZoneOffset.UTC)
            }
        )
    )
}