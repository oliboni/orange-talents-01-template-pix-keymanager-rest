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
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
@Requirements(
    Requires(classes = [StatusRuntimeException::class, ExceptionHandler::class])
)
class StatusRuntimeExceptionHandler:ExceptionHandler<StatusRuntimeException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException?): HttpResponse<*> {
        val status = when (exception!!.status.code) {
            INVALID_ARGUMENT -> BAD_REQUEST
            NOT_FOUND -> HttpStatus.NOT_FOUND
            ALREADY_EXISTS -> UNPROCESSABLE_ENTITY
            else -> INTERNAL_SERVER_ERROR
        }

        val response: MutableHttpResponse<*> = HttpResponse.status<Any>(status)

        return response.body(exception.status.description)

    }
}