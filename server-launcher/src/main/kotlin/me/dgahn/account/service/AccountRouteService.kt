package me.dgahn.account.service

import io.grpc.Status
import io.grpc.StatusException
import me.dgahn.account.AccountRouterGrpcKt
import me.dgahn.account.application.AccountSimpleService
import me.dgahn.account.exception.AccountNotFoundException
import me.dgahn.account.exception.EmailDuplicationException
import me.dgahn.account.exception.PasswordInvalidException
import me.dgahn.account.message.GetAllAccountByRequest
import me.dgahn.account.message.GetAllAccountByResponse
import me.dgahn.account.message.SignInRequest
import me.dgahn.account.message.SignInResponse
import me.dgahn.account.message.SignUpRequest
import me.dgahn.account.message.SignUpResponse
import me.dgahn.account.message.UpdateAccountRequest
import me.dgahn.account.message.UpdateAccountResponse
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

// API에 대한 경로 설정, Proto To Entity, Entity To Proto
@Suppress("TooGenericExceptionCaught")
class AccountRouteService : AccountRouterGrpcKt.AccountRouterCoroutineImplBase() {

    private val simpleService: AccountSimpleService = AccountSimpleService()

    override suspend fun signUp(request: SignUpRequest): SignUpResponse = try {
        throw NotImplementedError()
//        simpleService.signUp(request.toParam()).toSignUpResponse()
    } catch (e: EmailDuplicationException) {
        throw StatusException(Status.INVALID_ARGUMENT)
    } catch (e: Exception) {
        logger.error { e }
        throw StatusException(Status.INTERNAL)
    }

    override suspend fun signIn(request: SignInRequest): SignInResponse = try {
        throw NotImplementedError()
//        simpleService.signIn(request.toParam()).toSignInResponse()
    } catch (e: PasswordInvalidException) {
        throw StatusException(Status.INVALID_ARGUMENT)
    } catch (e: Exception) {
        logger.error { e }
        throw StatusException(Status.INTERNAL)
    }

    override suspend fun updateAccount(request: UpdateAccountRequest): UpdateAccountResponse = try {
        throw NotImplementedError()
//        simpleService.update(request.toParam()).toUpdateAccountResponse()
    } catch (e: PasswordInvalidException) {
        throw StatusException(Status.INVALID_ARGUMENT)
    } catch (e: AccountNotFoundException) {
        throw StatusException(Status.INVALID_ARGUMENT)
    } catch (e: Exception) {
        logger.error { e }
        throw StatusException(Status.INTERNAL)
    }

    override suspend fun getAllAccountBy(request: GetAllAccountByRequest): GetAllAccountByResponse = try {
        throw NotImplementedError()
//        simpleService.getAllAccount(request.toParam()).toGetAllAccountResponse()
    } catch (e: Exception) {
        throw StatusException(Status.INTERNAL)
    }
}
