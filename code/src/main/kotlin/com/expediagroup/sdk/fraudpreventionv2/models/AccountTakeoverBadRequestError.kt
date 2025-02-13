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
/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.expediagroup.sdk.fraudpreventionv2.models

import com.expediagroup.sdk.core.model.exception.client.PropertyConstraintViolationException
import com.expediagroup.sdk.fraudpreventionv2.models.AccountTakeoverBadRequestErrorAllOfCauses
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Valid
import javax.validation.Validation
import javax.validation.constraints.NotNull

/**
 * Indicates that a bad request occurred. Typically it is an invalid parameter.
 * @param code Snake cased all caps error code interpreted from the HTTP status code that can programmatically be acted upon.
 * @param message A human-readable explanation of the error, specific to this error occurrence.
 * @param causes
 */
data class AccountTakeoverBadRequestError(
    // Snake cased all caps error code interpreted from the HTTP status code that can programmatically be acted upon.
    @JsonProperty("code")
    @field:NotNull
    val code: AccountTakeoverBadRequestError.Code,
    // A human-readable explanation of the error, specific to this error occurrence.
    @JsonProperty("message")
    @field:NotNull
    @field:Valid
    val message: kotlin.String,
    @JsonProperty("causes")
    @field:Valid
    val causes: kotlin.collections.List<AccountTakeoverBadRequestErrorAllOfCauses>? = null
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var code: AccountTakeoverBadRequestError.Code? = null,
        private var message: kotlin.String? = null,
        private var causes: kotlin.collections.List<AccountTakeoverBadRequestErrorAllOfCauses>? = null
    ) {
        fun code(code: AccountTakeoverBadRequestError.Code) = apply { this.code = code }

        fun message(message: kotlin.String) = apply { this.message = message }

        fun causes(causes: kotlin.collections.List<AccountTakeoverBadRequestErrorAllOfCauses>?) = apply { this.causes = causes }

        fun build(): AccountTakeoverBadRequestError {
            val instance =
                AccountTakeoverBadRequestError(
                    code = code!!,
                    message = message!!,
                    causes = causes
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: AccountTakeoverBadRequestError) {
            val validator =
                Validation
                    .byDefaultProvider()
                    .configure()
                    .messageInterpolator(ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .validator

            val violations = validator.validate(instance)

            if (violations.isNotEmpty()) {
                throw PropertyConstraintViolationException(
                    constraintViolations = violations.map { "${it.propertyPath}: ${it.message}" }
                )
            }
        }
    }

    fun toBuilder() =
        Builder(
            code = code!!,
            message = message!!,
            causes = causes
        )

    /**
     * Snake cased all caps error code interpreted from the HTTP status code that can programmatically be acted upon.
     * Values: UNAUTHORIZED,FORBIDDEN,NOT_FOUND,ACCOUNT_UPDATE_NOT_FOUND,TOO_MANY_REQUESTS,INTERNAL_SERVER_ERROR,BAD_GATEWAY,RETRYABLE_ACCOUNT_SCREEN_FAILURE,RETRYABLE_ACCOUNT_UPDATE_FAILURE,GATEWAY_TIMEOUT,BAD_REQUEST
     */
    enum class Code(val value: kotlin.String) {
        @JsonProperty("UNAUTHORIZED")
        UNAUTHORIZED("UNAUTHORIZED"),

        @JsonProperty("FORBIDDEN")
        FORBIDDEN("FORBIDDEN"),

        @JsonProperty("NOT_FOUND")
        NOT_FOUND("NOT_FOUND"),

        @JsonProperty("ACCOUNT_UPDATE_NOT_FOUND")
        ACCOUNT_UPDATE_NOT_FOUND("ACCOUNT_UPDATE_NOT_FOUND"),

        @JsonProperty("TOO_MANY_REQUESTS")
        TOO_MANY_REQUESTS("TOO_MANY_REQUESTS"),

        @JsonProperty("INTERNAL_SERVER_ERROR")
        INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),

        @JsonProperty("BAD_GATEWAY")
        BAD_GATEWAY("BAD_GATEWAY"),

        @JsonProperty("RETRYABLE_ACCOUNT_SCREEN_FAILURE")
        RETRYABLE_ACCOUNT_SCREEN_FAILURE("RETRYABLE_ACCOUNT_SCREEN_FAILURE"),

        @JsonProperty("RETRYABLE_ACCOUNT_UPDATE_FAILURE")
        RETRYABLE_ACCOUNT_UPDATE_FAILURE("RETRYABLE_ACCOUNT_UPDATE_FAILURE"),

        @JsonProperty("GATEWAY_TIMEOUT")
        GATEWAY_TIMEOUT("GATEWAY_TIMEOUT"),

        @JsonProperty("BAD_REQUEST")
        BAD_REQUEST("BAD_REQUEST")
    }
}
