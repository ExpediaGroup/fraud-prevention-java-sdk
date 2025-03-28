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
import com.expediagroup.sdk.fraudpreventionv2.models.AccountTakeoverFraudDecision
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Valid
import javax.validation.Validation

/**
 * Response for an account transaction provided by Expedia's Fraud Prevention Service.
 * @param riskId Unique identifier assigned to the transaction by Expedia's Fraud Prevention Service.
 * @param decision
 */
data class AccountScreenResponse(
    // Unique identifier assigned to the transaction by Expedia's Fraud Prevention Service.
    @JsonProperty("risk_id")
    @field:Length(max = 200)
    @field:Valid
    val riskId: kotlin.String? = null,
    @JsonProperty("decision")
    @field:Valid
    val decision: AccountTakeoverFraudDecision? = null
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var riskId: kotlin.String? = null,
        private var decision: AccountTakeoverFraudDecision? = null
    ) {
        fun riskId(riskId: kotlin.String?) = apply { this.riskId = riskId }

        fun decision(decision: AccountTakeoverFraudDecision?) = apply { this.decision = decision }

        fun build(): AccountScreenResponse {
            val instance =
                AccountScreenResponse(
                    riskId = riskId,
                    decision = decision
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: AccountScreenResponse) {
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
            riskId = riskId,
            decision = decision
        )
}
