package br.com.zup.compartilhado.exception

import io.grpc.Status.Code.*
import io.grpc.Status.Code.NOT_FOUND
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.*
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
@Requirements(
    Requires(classes = [StatusRuntimeException::class, ExceptionHandler::class])
)
class StatusRuntimeExceptionHandler:ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val log = LoggerFactory.getLogger(StatusRuntimeExceptionHandler::class.java)

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {
        val statusCode = exception.status.code
        val statusDescription = exception.status.description ?: ""

        val (httpStatus,message) = when (statusCode) {
            INVALID_ARGUMENT -> Pair(BAD_REQUEST,"Dados da requisição estão inválidos")
            NOT_FOUND -> Pair(HttpStatus.NOT_FOUND,statusDescription)
            ALREADY_EXISTS -> Pair(UNPROCESSABLE_ENTITY,statusDescription)
            else -> {
                log.info("Erro inesperado '${exception.javaClass.name}' ao processar requisição", exception)
                Pair(INTERNAL_SERVER_ERROR,"Não foi possível completar a requisição. Erro: $statusDescription $statusCode")
            }
        }

        return HttpResponse.status<JsonError>(httpStatus).body(JsonError(message))

    }
}