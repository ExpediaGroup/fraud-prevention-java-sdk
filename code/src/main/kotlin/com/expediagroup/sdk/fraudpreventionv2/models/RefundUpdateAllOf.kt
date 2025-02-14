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
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Validation
import javax.validation.constraints.NotNull

/**
 *
 * @param refundStatus Identifies the refund status. Possible values are: -`ISSUED` - The refund was issued. -`SETTLED` - The refund was settled.
 */
data class RefundUpdateAllOf(
    // Identifies the refund status. Possible values are: -`ISSUED` - The refund was issued. -`SETTLED` - The refund was settled.
    @JsonProperty("refund_status")
    @field:NotNull
    val refundStatus: RefundUpdateAllOf.RefundStatus
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var refundStatus: RefundUpdateAllOf.RefundStatus? = null
    ) {
        fun refundStatus(refundStatus: RefundUpdateAllOf.RefundStatus) = apply { this.refundStatus = refundStatus }

        fun build(): RefundUpdateAllOf {
            val instance =
                RefundUpdateAllOf(
                    refundStatus = refundStatus!!
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: RefundUpdateAllOf) {
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
            refundStatus = refundStatus!!
        )

    /**
     * Identifies the refund status. Possible values are: -`ISSUED` - The refund was issued. -`SETTLED` - The refund was settled.
     * Values: ISSUED,SETTLED
     */
    enum class RefundStatus(val value: kotlin.String) {
        @JsonProperty("ISSUED")
        ISSUED("ISSUED"),

        @JsonProperty("SETTLED")
        SETTLED("SETTLED")
    }
}
