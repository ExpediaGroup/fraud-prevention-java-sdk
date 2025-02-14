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
import javax.validation.constraints.Pattern

/**
 * Address of a hotel.
 * @param addressLine1 Address line 1 of the address provided.
 * @param city City of the address provided.
 * @param state The two-characters ISO code for the state or province of the address.
 * @param zipCode Zip code of the address provided.
 * @param countryCode ISO alpha-3 country code of the address provided.
 * @param addressType
 * @param addressLine2 Address line 2 of the address provided.
 */
data class HotelAddress(
    // Address line 1 of the address provided.
    @JsonProperty("address_line1")
    @field:Length(max = 200)
    @field:NotNull
    @field:Valid
    val addressLine1: kotlin.String,
    // City of the address provided.
    @JsonProperty("city")
    @field:Length(max = 200)
    @field:NotNull
    @field:Valid
    val city: kotlin.String,
    // The two-characters ISO code for the state or province of the address.
    @JsonProperty("state")
    @field:Pattern(regexp = "^[A-Z]{2}$")
    @field:NotNull
    @field:Valid
    val state: kotlin.String,
    // Zip code of the address provided.
    @JsonProperty("zip_code")
    @field:Length(max = 20)
    @field:NotNull
    @field:Valid
    val zipCode: kotlin.String,
    // ISO alpha-3 country code of the address provided.
    @JsonProperty("country_code")
    @field:Pattern(regexp = "^[A-Z]{3}$")
    @field:NotNull
    @field:Valid
    val countryCode: kotlin.String,
    @JsonProperty("address_type")
    val addressType: HotelAddress.AddressType? = null,
    // Address line 2 of the address provided.
    @JsonProperty("address_line2")
    @field:Length(max = 200)
    @field:Valid
    val addressLine2: kotlin.String? = null
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var addressLine1: kotlin.String? = null,
        private var city: kotlin.String? = null,
        private var state: kotlin.String? = null,
        private var zipCode: kotlin.String? = null,
        private var countryCode: kotlin.String? = null,
        private var addressType: HotelAddress.AddressType? = null,
        private var addressLine2: kotlin.String? = null
    ) {
        fun addressLine1(addressLine1: kotlin.String) = apply { this.addressLine1 = addressLine1 }

        fun city(city: kotlin.String) = apply { this.city = city }

        fun state(state: kotlin.String) = apply { this.state = state }

        fun zipCode(zipCode: kotlin.String) = apply { this.zipCode = zipCode }

        fun countryCode(countryCode: kotlin.String) = apply { this.countryCode = countryCode }

        fun addressType(addressType: HotelAddress.AddressType?) = apply { this.addressType = addressType }

        fun addressLine2(addressLine2: kotlin.String?) = apply { this.addressLine2 = addressLine2 }

        fun build(): HotelAddress {
            val instance =
                HotelAddress(
                    addressLine1 = addressLine1!!,
                    city = city!!,
                    state = state!!,
                    zipCode = zipCode!!,
                    countryCode = countryCode!!,
                    addressType = addressType,
                    addressLine2 = addressLine2
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: HotelAddress) {
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
            addressLine1 = addressLine1!!,
            city = city!!,
            state = state!!,
            zipCode = zipCode!!,
            countryCode = countryCode!!,
            addressType = addressType,
            addressLine2 = addressLine2
        )

    /**
     *
     * Values: HOME,WORK
     */
    enum class AddressType(val value: kotlin.String) {
        @JsonProperty("HOME")
        HOME("HOME"),

        @JsonProperty("WORK")
        WORK("WORK")
    }
}
