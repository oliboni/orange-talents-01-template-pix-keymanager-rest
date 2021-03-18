package br.com.zup.compartilhado

import br.com.zup.NovaChaveResponse
import br.com.zup.chavePix.cadastro.NovaChavePixResponse

fun NovaChaveResponse.toModel(): NovaChavePixResponse{
    return NovaChavePixResponse(clienteId,pixId)
}