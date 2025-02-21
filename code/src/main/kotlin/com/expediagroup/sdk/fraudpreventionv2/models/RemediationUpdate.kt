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
import com.expediagroup.sdk.fraudpreventionv2.models.AccountUpdateRequest
import com.expediagroup.sdk.fraudpreventionv2.models.RemediationUpdateAction
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Valid
import javax.validation.Validation
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Information specific to remediation actions initiated by the Partner's system to a user as a result of a fraud recommendation.
 * @param remediationUpdateActions
 */
data class RemediationUpdate(
    // The `risk_id` provided by Expedia's Fraud Prevention Service in the `AccountScreenResponse`.
    @JsonProperty("risk_id")
    @field:Length(max = 200)
    @field:NotNull
    @field:Valid
    override val riskId: kotlin.String,
    @JsonProperty("remediation_update_actions")
    @field:Size(min = 1, max = 20)
    @field:NotNull
    @field:Valid
    val remediationUpdateActions: kotlin.collections
        .List<
            RemediationUpdateAction
        >
) : AccountUpdateRequest {
    @JsonProperty("type")
    override val type: AccountUpdateRequest.Type = AccountUpdateRequest.Type.REMEDIATION_UPDATE

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var riskId: kotlin.String? = null,
        private var remediationUpdateActions: kotlin.collections.List<RemediationUpdateAction>? = null
    ) {
        fun riskId(riskId: kotlin.String) = apply { this.riskId = riskId }

        fun remediationUpdateActions(remediationUpdateActions: kotlin.collections.List<RemediationUpdateAction>) = apply { this.remediationUpdateActions = remediationUpdateActions }

        fun build(): RemediationUpdate {
            val instance =
                RemediationUpdate(
                    riskId = riskId!!,
                    remediationUpdateActions = remediationUpdateActions!!
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: RemediationUpdate) {
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
            riskId = riskId!!,
            remediationUpdateActions = remediationUpdateActions!!
        )
}
