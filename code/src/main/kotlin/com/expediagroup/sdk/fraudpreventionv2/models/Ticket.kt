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
 * @param type Specifies the type of the ticket, such as ADULT, CHILD, SENIOR, STUDENT, or OTHER.
 * @param quantity This field represents the count or number of tickets associated with the type.
 */
data class Ticket(
    // Specifies the type of the ticket, such as ADULT, CHILD, SENIOR, STUDENT, or OTHER.
    @JsonProperty("type")
    @field:NotNull
    val type: Ticket.Type,
    // This field represents the count or number of tickets associated with the type.
    @JsonProperty("quantity")
    val quantity: kotlin.Int
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var type: Ticket.Type? = null,
        private var quantity: kotlin.Int? = null
    ) {
        fun type(type: Ticket.Type) = apply { this.type = type }

        fun quantity(quantity: kotlin.Int) = apply { this.quantity = quantity }

        fun build(): Ticket {
            val instance =
                Ticket(
                    type = type!!,
                    quantity = quantity!!
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: Ticket) {
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
            type = type!!,
            quantity = quantity!!
        )

    /**
     * Specifies the type of the ticket, such as ADULT, CHILD, SENIOR, STUDENT, or OTHER.
     * Values: ADULT,CHILD,SENIOR,STUDENT,OTHER
     */
    enum class Type(val value: kotlin.String) {
        @JsonProperty("ADULT")
        ADULT("ADULT"),

        @JsonProperty("CHILD")
        CHILD("CHILD"),

        @JsonProperty("SENIOR")
        SENIOR("SENIOR"),

        @JsonProperty("STUDENT")
        STUDENT("STUDENT"),

        @JsonProperty("OTHER")
        OTHER("OTHER")
    }
}
