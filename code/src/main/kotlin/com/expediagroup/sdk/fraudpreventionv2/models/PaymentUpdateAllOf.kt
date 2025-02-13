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
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Valid
import javax.validation.Validation
import javax.validation.constraints.NotNull

/**
 *
 * @param merchantOrderCode Reference code passed to acquiring bank at the time of payment. This code is the key ID that ties back to payments data at the payment level.
 */
data class PaymentUpdateAllOf(
    // Reference code passed to acquiring bank at the time of payment. This code is the key ID that ties back to payments data at the payment level.
    @JsonProperty("merchant_order_code")
    @field:Length(max = 200)
    @field:NotNull
    @field:Valid
    val merchantOrderCode: kotlin.String
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var merchantOrderCode: kotlin.String? = null
    ) {
        fun merchantOrderCode(merchantOrderCode: kotlin.String) = apply { this.merchantOrderCode = merchantOrderCode }

        fun build(): PaymentUpdateAllOf {
            val instance =
                PaymentUpdateAllOf(
                    merchantOrderCode = merchantOrderCode!!
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: PaymentUpdateAllOf) {
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
            merchantOrderCode = merchantOrderCode!!
        )
}
