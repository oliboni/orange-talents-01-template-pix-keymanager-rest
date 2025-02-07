package br.com.zup.chavePix

import br.com.zup.*
import br.com.zup.chavePix.cadastro.NovaChavePixRequest
import br.com.zup.chavePix.cadastro.NovaChavePixResponse
import br.com.zup.chavePix.consulta.ConsultaChaveByClienteResponse
import br.com.zup.chavePix.consulta.ConsultaChavePixResponse
import br.com.zup.compartilhado.toModel
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.validation.Valid
import kotlin.reflect.KFunction1

@Validated
@Controller("/api/chavePix/clientes/{clienteId}")
class ChavePixController(@Inject val grpcPix: PixServiceGrpc.PixServiceBlockingStub) {

    private val LOG = LoggerFactory.getLogger(ChavePixController::class.java)

    @Post
    fun registro(clienteId: UUID, @Body @Valid request: NovaChavePixRequest): HttpResponse<NovaChavePixResponse> {
        LOG.info("Gerando nova chave pix para cliente $clienteId")

        val response = NovaChaveRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoChave(TipoChave.valueOf(request.tipoChave?.name!!))
            .setValorChave(request.valorChave)
            .setTipoConta(request.tipoConta)
            .build().let {
                grpcPix.registro(it)
            }.toModel()

        LOG.info("Chave pix ${response.pixId} gerada com sucesso!")

        val location = HttpResponse.uri("/api/chavePix/clientes/${response.clienteId}/pix/${response.pixId}")
        return HttpResponse.created(location)
    }

    @Delete("pix/{pixId}")
    fun remove(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        LOG.info("Removendo chave pix $pixId, cliente $clienteId")

        RemoveChaveRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setChavePixId(pixId.toString())
            .build().let {
                grpcPix.remove(it)
            }
        LOG.info("Chave pix $pixId removida com sucesso!")

        return HttpResponse.ok()
    }

    @Get("pix/{pixId}")
    fun consultaChave(clienteId: UUID, pixId: UUID): HttpResponse<ConsultaChavePixResponse> {
        LOG.info("Consultando chave pix $pixId, cliente $clienteId")
        val response = ConsultaChaveRequest.newBuilder()
            .setChavePixId(
                ConsultaChaveRequest.FiltroPorPixId.newBuilder()
                    .setClienteId(clienteId.toString())
                    .setPixId(pixId.toString())
                    .build()
            )
            .build().let {
                grpcPix.consulta(it)
            }.toModel()

        return HttpResponse.ok(response)
    }

    @Get
    fun listaChaves(clienteId: UUID): HttpResponse<Any> {
        LOG.info("Consultando lista de chaves, cliente $clienteId")
        val lista = ConsultaByClienteRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build().let {
                grpcPix.consultaByCliente(it)
            }.chavePixList.map { ConsultaChaveByClienteResponse(it) }

        return HttpResponse.ok(lista)
    }
}