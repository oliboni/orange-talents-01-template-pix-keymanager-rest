package br.com.zup.chavePix.consulta

import java.time.LocalDateTime

data class ConsultaChavePixResponse(
    val clienteId: String,
    val chavePixId: String,
    val chavePix: ChavePixResponse,
)

data class ChavePixResponse(
    val tipoChave: String,
    val chave: String,
    val conta: ContaInfoResponse,
    val criadoEm: LocalDateTime
)

data class ContaInfoResponse(
    val tipoConta: String,
    val instituicao: String,
    val nomeTitular: String,
    val cpfTitular: String,
    val agencia: String,
    val numero: String
)
