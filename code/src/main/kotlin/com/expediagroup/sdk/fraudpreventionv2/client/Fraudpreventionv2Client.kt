/*
 * Copyright (C) 2022 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expediagroup.sdk.fraudpreventionv2.client

import com.expediagroup.sdk.core.client.ExpediaGroupClient
import com.expediagroup.sdk.core.configuration.ExpediaGroupClientConfiguration
import com.expediagroup.sdk.core.constant.ConfigurationName
import com.expediagroup.sdk.core.constant.HeaderKey
import com.expediagroup.sdk.core.constant.provider.ExceptionMessageProvider.getMissingRequiredConfigurationMessage
import com.expediagroup.sdk.core.model.EmptyResponse
import com.expediagroup.sdk.core.model.Operation
import com.expediagroup.sdk.core.model.Response
import com.expediagroup.sdk.core.model.exception.client.ExpediaGroupConfigurationException
import com.expediagroup.sdk.core.model.exception.handle
import com.expediagroup.sdk.fraudpreventionv2.models.*
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ErrorObjectMapper
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiAccountTakeoverBadRequestErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiAccountUpdateNotFoundErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiBadGatewayErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiBadRequestErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiForbiddenErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiGatewayTimeoutErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiInternalServerErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiNotFoundErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiRetryableOrderPurchaseScreenFailureException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiServiceUnavailableErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiTooManyRequestsErrorException
import com.expediagroup.sdk.fraudpreventionv2.models.exception.ExpediaGroupApiUnauthorizedErrorException
import com.expediagroup.sdk.fraudpreventionv2.operations.ScreenAccountOperation
import com.expediagroup.sdk.fraudpreventionv2.operations.ScreenOrderPurchaseOperation
import com.expediagroup.sdk.fraudpreventionv2.operations.UpdateAccountOperation
import com.expediagroup.sdk.fraudpreventionv2.operations.UpdateOrderPurchaseOperation
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

/**
* Fraud & Risk APIs to detect fraud
*/

class Fraudpreventionv2Client private constructor(clientConfiguration: ExpediaGroupClientConfiguration) : ExpediaGroupClient("fraudpreventionv2", clientConfiguration) {
    class Builder : ExpediaGroupClient.Builder<Builder>() {
        override fun build() =
            Fraudpreventionv2Client(
                ExpediaGroupClientConfiguration(key, secret, endpoint, requestTimeout, connectionTimeout, socketTimeout, maskedLoggingHeaders, maskedLoggingBodyFields, null, authEndpoint)
            )
    }

    class BuilderWithHttpClient() : ExpediaGroupClient.BuilderWithHttpClient<BuilderWithHttpClient>() {
        override fun build(): Fraudpreventionv2Client {
            if (okHttpClient == null) {
                throw ExpediaGroupConfigurationException(getMissingRequiredConfigurationMessage(ConfigurationName.OKHTTP_CLIENT))
            }

            return Fraudpreventionv2Client(
                ExpediaGroupClientConfiguration(key, secret, endpoint, null, null, null, maskedLoggingHeaders, maskedLoggingBodyFields, okHttpClient, authEndpoint)
            )
        }
    }

    companion object {
        @JvmStatic fun builder() = Builder()

        @JvmStatic fun builderWithHttpClient() = BuilderWithHttpClient()
    }

    override suspend fun throwServiceException(
        response: HttpResponse,
        operationId: String
    ): Unit = throw ErrorObjectMapper.process(response, operationId)

    private suspend inline fun <reified RequestType> executeHttpRequest(operation: Operation<RequestType>): HttpResponse =
        httpClient.request {
            method = HttpMethod.parse(operation.method)
            url(operation.url)

            operation.params?.getHeaders()?.let {
                headers.appendAll(it)
            }

            operation.params?.getQueryParams()?.let {
                url.parameters.appendAll(it)
            }

            val extraHeaders =
                buildMap {
                    put(HeaderKey.TRANSACTION_ID, operation.transactionId.dequeue().toString())
                }

            appendHeaders(extraHeaders)
            contentType(ContentType.Application.Json)
            setBody(operation.requestBody)
        }

    private inline fun <reified RequestType> executeWithEmptyResponse(operation: Operation<RequestType>): EmptyResponse {
        try {
            return executeAsyncWithEmptyResponse(operation).get()
        } catch (exception: Exception) {
            exception.handle()
        }
    }

    private inline fun <reified RequestType> executeAsyncWithEmptyResponse(operation: Operation<RequestType>): CompletableFuture<EmptyResponse> =
        GlobalScope.future(Dispatchers.IO) {
            try {
                val response = executeHttpRequest(operation)
                throwIfError(response, operation.operationId)
                EmptyResponse(response.status.value, response.headers.entries())
            } catch (exception: Exception) {
                exception.handle()
            }
        }

    private inline fun <reified RequestType, reified ResponseType> execute(operation: Operation<RequestType>): Response<ResponseType> {
        try {
            return executeAsync<RequestType, ResponseType>(operation).get()
        } catch (exception: Exception) {
            exception.handle()
        }
    }

    private inline fun <reified RequestType, reified ResponseType> executeAsync(operation: Operation<RequestType>): CompletableFuture<Response<ResponseType>> =
        GlobalScope.future(Dispatchers.IO) {
            try {
                val response = executeHttpRequest(operation)
                throwIfError(response, operation.operationId)
                Response(response.status.value, response.body<ResponseType>(), response.headers.entries())
            } catch (exception: Exception) {
                exception.handle()
            }
        }

    /**
     * Run fraud screening for one transaction
     * The Account Screen API gives a Fraud recommendation for an account transaction. A recommendation can be ACCEPT, CHALLENGE, or REJECT. A transaction is marked as CHALLENGE whenever there are insufficient signals to recommend ACCEPT or REJECT. These CHALLENGE incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param operation [ScreenAccountOperation]
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type AccountScreenResponse
     */
    fun execute(operation: ScreenAccountOperation): Response<AccountScreenResponse> = execute<AccountScreenRequest, AccountScreenResponse>(operation)

    /**
     * Run fraud screening for one transaction
     * The Account Screen API gives a Fraud recommendation for an account transaction. A recommendation can be ACCEPT, CHALLENGE, or REJECT. A transaction is marked as CHALLENGE whenever there are insufficient signals to recommend ACCEPT or REJECT. These CHALLENGE incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param operation [ScreenAccountOperation]
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [CompletableFuture<Response>] object with a body of type AccountScreenResponse
     */
    fun executeAsync(operation: ScreenAccountOperation): CompletableFuture<Response<AccountScreenResponse>> = executeAsync<AccountScreenRequest, AccountScreenResponse>(operation)

    private suspend inline fun kscreenAccountWithResponse(accountScreenRequest: AccountScreenRequest): Response<AccountScreenResponse> {
        val operation =
            ScreenAccountOperation(
                accountScreenRequest
            )

        return execute(operation)
    }

    /**
     * Run fraud screening for one transaction
     * The Account Screen API gives a Fraud recommendation for an account transaction. A recommendation can be ACCEPT, CHALLENGE, or REJECT. A transaction is marked as CHALLENGE whenever there are insufficient signals to recommend ACCEPT or REJECT. These CHALLENGE incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param accountScreenRequest
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return AccountScreenResponse
     */
    @Throws(
        ExpediaGroupApiAccountTakeoverBadRequestErrorException::class,
        ExpediaGroupApiAccountTakeoverUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiServiceUnavailableErrorException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: ScreenAccountOperation)"))
    fun screenAccount(accountScreenRequest: AccountScreenRequest): AccountScreenResponse = screenAccountWithResponse(accountScreenRequest).data

    /**
     * Run fraud screening for one transaction
     * The Account Screen API gives a Fraud recommendation for an account transaction. A recommendation can be ACCEPT, CHALLENGE, or REJECT. A transaction is marked as CHALLENGE whenever there are insufficient signals to recommend ACCEPT or REJECT. These CHALLENGE incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param accountScreenRequest
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type AccountScreenResponse
     */
    @Throws(
        ExpediaGroupApiAccountTakeoverBadRequestErrorException::class,
        ExpediaGroupApiAccountTakeoverUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiServiceUnavailableErrorException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: ScreenAccountOperation)"))
    fun screenAccountWithResponse(accountScreenRequest: AccountScreenRequest): Response<AccountScreenResponse> {
        try {
            return GlobalScope.future(Dispatchers.IO) {
                kscreenAccountWithResponse(accountScreenRequest)
            }.get()
        } catch (exception: Exception) {
            exception.handle()
        }
    }

    /**
     * Run fraud screening for one transaction
     * The Order Purchase API gives a Fraud recommendation for a transaction. A recommendation can be Accept, Reject, or Review. A transaction is marked as Review whenever there are insufficient signals to recommend Accept or Reject. These incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param operation [ScreenOrderPurchaseOperation]
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseScreenFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type OrderPurchaseScreenResponse
     */
    fun execute(operation: ScreenOrderPurchaseOperation): Response<OrderPurchaseScreenResponse> = execute<OrderPurchaseScreenRequest, OrderPurchaseScreenResponse>(operation)

    /**
     * Run fraud screening for one transaction
     * The Order Purchase API gives a Fraud recommendation for a transaction. A recommendation can be Accept, Reject, or Review. A transaction is marked as Review whenever there are insufficient signals to recommend Accept or Reject. These incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param operation [ScreenOrderPurchaseOperation]
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseScreenFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [CompletableFuture<Response>] object with a body of type OrderPurchaseScreenResponse
     */
    fun executeAsync(operation: ScreenOrderPurchaseOperation): CompletableFuture<Response<OrderPurchaseScreenResponse>> =
        executeAsync<OrderPurchaseScreenRequest, OrderPurchaseScreenResponse>(operation)

    private suspend inline fun kscreenOrderPurchaseWithResponse(orderPurchaseScreenRequest: OrderPurchaseScreenRequest): Response<OrderPurchaseScreenResponse> {
        val operation =
            ScreenOrderPurchaseOperation(
                orderPurchaseScreenRequest
            )

        return execute(operation)
    }

    /**
     * Run fraud screening for one transaction
     * The Order Purchase API gives a Fraud recommendation for a transaction. A recommendation can be Accept, Reject, or Review. A transaction is marked as Review whenever there are insufficient signals to recommend Accept or Reject. These incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param orderPurchaseScreenRequest
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseScreenFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return OrderPurchaseScreenResponse
     */
    @Throws(
        ExpediaGroupApiBadRequestErrorException::class,
        ExpediaGroupApiUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiRetryableOrderPurchaseScreenFailureException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: ScreenOrderPurchaseOperation)"))
    fun screenOrderPurchase(orderPurchaseScreenRequest: OrderPurchaseScreenRequest): OrderPurchaseScreenResponse = screenOrderPurchaseWithResponse(orderPurchaseScreenRequest).data

    /**
     * Run fraud screening for one transaction
     * The Order Purchase API gives a Fraud recommendation for a transaction. A recommendation can be Accept, Reject, or Review. A transaction is marked as Review whenever there are insufficient signals to recommend Accept or Reject. These incidents are manually reviewed, and a corrected recommendation is made asynchronously.
     * @param orderPurchaseScreenRequest
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseScreenFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type OrderPurchaseScreenResponse
     */
    @Throws(
        ExpediaGroupApiBadRequestErrorException::class,
        ExpediaGroupApiUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiRetryableOrderPurchaseScreenFailureException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: ScreenOrderPurchaseOperation)"))
    fun screenOrderPurchaseWithResponse(orderPurchaseScreenRequest: OrderPurchaseScreenRequest): Response<OrderPurchaseScreenResponse> {
        try {
            return GlobalScope.future(Dispatchers.IO) {
                kscreenOrderPurchaseWithResponse(orderPurchaseScreenRequest)
            }.get()
        } catch (exception: Exception) {
            exception.handle()
        }
    }

    /**
     * Send an update as a result of an account screen transaction
     * The Account Update API is called when there is an account lifecycle transition such as a challenge outcome, account restoration, or remediation action completion. For example, if a user's account is disabled, deleted, or restored, the Account Update API is called to notify Expedia Group about the change. The Account Update API is also called when a user responds to a login Multi-Factor Authentication based on a Fraud recommendation.
     * @param operation [UpdateAccountOperation]
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiAccountUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type AccountUpdateResponse
     */
    fun execute(operation: UpdateAccountOperation): Response<AccountUpdateResponse> = execute<AccountUpdateRequest, AccountUpdateResponse>(operation)

    /**
     * Send an update as a result of an account screen transaction
     * The Account Update API is called when there is an account lifecycle transition such as a challenge outcome, account restoration, or remediation action completion. For example, if a user's account is disabled, deleted, or restored, the Account Update API is called to notify Expedia Group about the change. The Account Update API is also called when a user responds to a login Multi-Factor Authentication based on a Fraud recommendation.
     * @param operation [UpdateAccountOperation]
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiAccountUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [CompletableFuture<Response>] object with a body of type AccountUpdateResponse
     */
    fun executeAsync(operation: UpdateAccountOperation): CompletableFuture<Response<AccountUpdateResponse>> = executeAsync<AccountUpdateRequest, AccountUpdateResponse>(operation)

    private suspend inline fun kupdateAccountWithResponse(accountUpdateRequest: AccountUpdateRequest): Response<AccountUpdateResponse> {
        val operation =
            UpdateAccountOperation(
                accountUpdateRequest
            )

        return execute(operation)
    }

    /**
     * Send an update as a result of an account screen transaction
     * The Account Update API is called when there is an account lifecycle transition such as a challenge outcome, account restoration, or remediation action completion. For example, if a user's account is disabled, deleted, or restored, the Account Update API is called to notify Expedia Group about the change. The Account Update API is also called when a user responds to a login Multi-Factor Authentication based on a Fraud recommendation.
     * @param accountUpdateRequest An AccountUpdate request may be of one of the following types `MULTI_FACTOR_AUTHENTICATION_UPDATE`, `REMEDIATION_UPDATE`.
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiAccountUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return AccountUpdateResponse
     */
    @Throws(
        ExpediaGroupApiAccountTakeoverBadRequestErrorException::class,
        ExpediaGroupApiAccountTakeoverUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiAccountUpdateNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiServiceUnavailableErrorException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: UpdateAccountOperation)"))
    fun updateAccount(accountUpdateRequest: AccountUpdateRequest): AccountUpdateResponse = updateAccountWithResponse(accountUpdateRequest).data

    /**
     * Send an update as a result of an account screen transaction
     * The Account Update API is called when there is an account lifecycle transition such as a challenge outcome, account restoration, or remediation action completion. For example, if a user's account is disabled, deleted, or restored, the Account Update API is called to notify Expedia Group about the change. The Account Update API is also called when a user responds to a login Multi-Factor Authentication based on a Fraud recommendation.
     * @param accountUpdateRequest An AccountUpdate request may be of one of the following types `MULTI_FACTOR_AUTHENTICATION_UPDATE`, `REMEDIATION_UPDATE`.
     * @throws ExpediaGroupApiAccountTakeoverBadRequestErrorException
     * @throws ExpediaGroupApiAccountTakeoverUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiAccountUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiServiceUnavailableErrorException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type AccountUpdateResponse
     */
    @Throws(
        ExpediaGroupApiAccountTakeoverBadRequestErrorException::class,
        ExpediaGroupApiAccountTakeoverUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiAccountUpdateNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiServiceUnavailableErrorException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: UpdateAccountOperation)"))
    fun updateAccountWithResponse(accountUpdateRequest: AccountUpdateRequest): Response<AccountUpdateResponse> {
        try {
            return GlobalScope.future(Dispatchers.IO) {
                kupdateAccountWithResponse(accountUpdateRequest)
            }.get()
        } catch (exception: Exception) {
            exception.handle()
        }
    }

    /**
     * Send an update for a transaction
     * The Order Purchase Update API is called when the status of the order has changed.  For example, if the customer cancels the reservation, changes reservation in any way, or adds additional products or travelers to the reservation, the Order Purchase Update API is called to notify Expedia Group about the change.  The Order Purchase Update API is also called when the merchant cancels or changes an order based on a Fraud recommendation.
     * @param operation [UpdateOrderPurchaseOperation]
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type OrderPurchaseUpdateResponse
     */
    fun execute(operation: UpdateOrderPurchaseOperation): Response<OrderPurchaseUpdateResponse> = execute<OrderPurchaseUpdateRequest, OrderPurchaseUpdateResponse>(operation)

    /**
     * Send an update for a transaction
     * The Order Purchase Update API is called when the status of the order has changed.  For example, if the customer cancels the reservation, changes reservation in any way, or adds additional products or travelers to the reservation, the Order Purchase Update API is called to notify Expedia Group about the change.  The Order Purchase Update API is also called when the merchant cancels or changes an order based on a Fraud recommendation.
     * @param operation [UpdateOrderPurchaseOperation]
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [CompletableFuture<Response>] object with a body of type OrderPurchaseUpdateResponse
     */
    fun executeAsync(operation: UpdateOrderPurchaseOperation): CompletableFuture<Response<OrderPurchaseUpdateResponse>> =
        executeAsync<OrderPurchaseUpdateRequest, OrderPurchaseUpdateResponse>(operation)

    private suspend inline fun kupdateOrderPurchaseWithResponse(orderPurchaseUpdateRequest: OrderPurchaseUpdateRequest): Response<OrderPurchaseUpdateResponse> {
        val operation =
            UpdateOrderPurchaseOperation(
                orderPurchaseUpdateRequest
            )

        return execute(operation)
    }

    /**
     * Send an update for a transaction
     * The Order Purchase Update API is called when the status of the order has changed.  For example, if the customer cancels the reservation, changes reservation in any way, or adds additional products or travelers to the reservation, the Order Purchase Update API is called to notify Expedia Group about the change.  The Order Purchase Update API is also called when the merchant cancels or changes an order based on a Fraud recommendation.
     * @param orderPurchaseUpdateRequest An OrderPurchaseUpdate request may be of one of the following types `ORDER_UPDATE`, `CHARGEBACK_FEEDBACK`, `INSULT_FEEDBACK`, `REFUND_UPDATE`, `PAYMENT_UPDATE`.
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return OrderPurchaseUpdateResponse
     */
    @Throws(
        ExpediaGroupApiBadRequestErrorException::class,
        ExpediaGroupApiUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: UpdateOrderPurchaseOperation)"))
    fun updateOrderPurchase(orderPurchaseUpdateRequest: OrderPurchaseUpdateRequest): OrderPurchaseUpdateResponse = updateOrderPurchaseWithResponse(orderPurchaseUpdateRequest).data

    /**
     * Send an update for a transaction
     * The Order Purchase Update API is called when the status of the order has changed.  For example, if the customer cancels the reservation, changes reservation in any way, or adds additional products or travelers to the reservation, the Order Purchase Update API is called to notify Expedia Group about the change.  The Order Purchase Update API is also called when the merchant cancels or changes an order based on a Fraud recommendation.
     * @param orderPurchaseUpdateRequest An OrderPurchaseUpdate request may be of one of the following types `ORDER_UPDATE`, `CHARGEBACK_FEEDBACK`, `INSULT_FEEDBACK`, `REFUND_UPDATE`, `PAYMENT_UPDATE`.
     * @throws ExpediaGroupApiBadRequestErrorException
     * @throws ExpediaGroupApiUnauthorizedErrorException
     * @throws ExpediaGroupApiForbiddenErrorException
     * @throws ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException
     * @throws ExpediaGroupApiTooManyRequestsErrorException
     * @throws ExpediaGroupApiInternalServerErrorException
     * @throws ExpediaGroupApiBadGatewayErrorException
     * @throws ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException
     * @throws ExpediaGroupApiGatewayTimeoutErrorException
     * @return a [Response] object with a body of type OrderPurchaseUpdateResponse
     */
    @Throws(
        ExpediaGroupApiBadRequestErrorException::class,
        ExpediaGroupApiUnauthorizedErrorException::class,
        ExpediaGroupApiForbiddenErrorException::class,
        ExpediaGroupApiOrderPurchaseUpdateNotFoundErrorException::class,
        ExpediaGroupApiTooManyRequestsErrorException::class,
        ExpediaGroupApiInternalServerErrorException::class,
        ExpediaGroupApiBadGatewayErrorException::class,
        ExpediaGroupApiRetryableOrderPurchaseUpdateFailureException::class,
        ExpediaGroupApiGatewayTimeoutErrorException::class
    )
    @JvmOverloads
    @Deprecated("Use execute method instead", ReplaceWith("execute(operation: UpdateOrderPurchaseOperation)"))
    fun updateOrderPurchaseWithResponse(orderPurchaseUpdateRequest: OrderPurchaseUpdateRequest): Response<OrderPurchaseUpdateResponse> {
        try {
            return GlobalScope.future(Dispatchers.IO) {
                kupdateOrderPurchaseWithResponse(orderPurchaseUpdateRequest)
            }.get()
        } catch (exception: Exception) {
            exception.handle()
        }
    }
}
