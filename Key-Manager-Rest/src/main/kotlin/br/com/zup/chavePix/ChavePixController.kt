package br.com.zup.chavePix

import br.com.zup.NovaChaveRequest
import br.com.zup.PixServiceGrpc
import br.com.zup.chavePix.cadastro.NovaChavePixRequest
import br.com.zup.chavePix.cadastro.NovaChavePixResponse
import br.com.zup.compartilhado.toModel
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/chavePix")
class ChavePixController(@Inject val grpcPix: PixServiceGrpc.PixServiceBlockingStub) {

    @Post
    fun registro(@Body @Valid request: NovaChavePixRequest): HttpResponse<NovaChavePixResponse> {
        val response = NovaChaveRequest.newBuilder()
            .setClienteId(request.clienteId)
            .setTipoChave(request.tipoChave)
            .setValorChave(request.valorChave)
            .setTipoConta(request.tipoConta)
            .build().let {
                grpcPix.registro(it)
            }.toModel()

        return HttpResponse.ok(response)
    }
}