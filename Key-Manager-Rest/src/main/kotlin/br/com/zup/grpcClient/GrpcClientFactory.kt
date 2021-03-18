package br.com.zup.grpcClient

import br.com.zup.PixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {
    @Singleton
    fun pixClientStub(@GrpcChannel("pix") channel: ManagedChannel): PixServiceGrpc.PixServiceBlockingStub? {
        return PixServiceGrpc.newBlockingStub(channel)
    }
}