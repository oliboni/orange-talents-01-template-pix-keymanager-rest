package br.com.zup.chavePix.cadastro

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.compartilhado.validations.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class NovaChavePixRequest (
    @field:NotBlank
    @field:ValidUUID
    val clienteId: String?,

    @field:NotNull
    val tipoChave: TipoChave?,

    val valorChave: String?,

    @field:NotNull
    val tipoConta: TipoConta?

)
