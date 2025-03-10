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
import com.expediagroup.sdk.fraudpreventionv2.models.AirSegment
import com.expediagroup.sdk.fraudpreventionv2.models.Amount
import com.expediagroup.sdk.fraudpreventionv2.models.TravelProduct
import com.expediagroup.sdk.fraudpreventionv2.models.TravelProductType
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Valid
import javax.validation.Validation
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 *
 * @param departureTime Local date and time of departure from original departure location, in ISO-8601 date and time format `yyyy-MM-ddTHH:mm:ss.SSSZ`.
 * @param arrivalTime Local date and time of arrival to final destination location, in ISO-8601 date and time format `yyyy-MM-ddTHH:mm:ss.SSSZ`.
 * @param airSegments Additional airline and flight details for each of the trip segments.
 * @param flightType Identifies the type of air trip based on the air destinations.
 * @param passengerNameRecord Airline booking confirmation code for the trip.
 * @param globalDistributionSystemType Associated with Passenger Name Record (PNR).
 */
data class Air(
    @JsonProperty("price")
    @field:NotNull
    @field:Valid
    override val price: Amount,
    // Type of inventory. Ensure attributes mentioned in dictionary below are set to corresponding values only. `inventory_type` has the following mapping with TravelProduct `type` attribute: *       inventory_type            :      type * ------------------------------------------------------ *  `Cruise`                       : `CRUISE` *  `Air`                          : `AIR` *  `Car`                          : `CAR` *  `Insurance`                    : `INSURANCE` *  `Hotel`                        : `HOTEL` *  `Rail`                         : `RAIL` *  `Activity`                     : `ACTIVITY`
    @JsonProperty("inventory_type")
    @field:Length(max = 30)
    @field:NotNull
    @field:Valid
    override val inventoryType: kotlin.String,
    // Identifies the business model through which the supply is being sold. Merchant/Agency. * `MERCHANT` is used when Partner is the merchant of record for this order. * `AGENCY` is used when this order is through an agency booking.
    @JsonProperty("inventory_source")
    @field:NotNull
    override val inventorySource: TravelProduct.InventorySource,
    // Local date and time of departure from original departure location, in ISO-8601 date and time format `yyyy-MM-ddTHH:mm:ss.SSSZ`.
    @JsonProperty("departure_time")
    val departureTime: java.time.OffsetDateTime,
    // Local date and time of arrival to final destination location, in ISO-8601 date and time format `yyyy-MM-ddTHH:mm:ss.SSSZ`.
    @JsonProperty("arrival_time")
    val arrivalTime: java.time.OffsetDateTime,
    // Additional airline and flight details for each of the trip segments.
    @JsonProperty("air_segments")
    @field:Size(min = 1, max = 30)
    @field:NotNull
    @field:Valid
    val airSegments: kotlin.collections
        .List<
            AirSegment
        >,
    // List of travelerGuids who are part of the traveling party on the order for the product. Information for each product and its required travelers should be provided in the API request. If the product booking does not require accompanying quest information then that does not need to be provided in the API request. Example: * For Air products, all travelers' details are required to complete the booking. * For Hotel products, typically the details on the person checking-in is required. * For Car products, typically only the primary driver information is required. If multiple traveler details are in the itinerary, this structure allows to fill up traveler details once in the `travelers` section, and then associate individual products to the respective travelers. This association is made using `traveler_id` field. A GUID can be generated for each object in the `travelers` section. The same GUID can be provided in the `traveler_references` below. The `travelers` array should have at least one `traveler` object, and each `traveler` object should have a `traveler_id` which is not necessarily an account id. Example: *   Travelers * ------------ *  A - GUID1 *  B - GUID2 *  C - GUID3 * *   Products * ------------ * Air *   [GUID1, GUID2, GUID3] * Hotel *   [GUID1] * Car *   [GUID3] * Rail *   [GUID2] * Activity *   [GUID1] * The example above demonstrates the association of travelers with various products. * All three travelers (A, B, and C) are associated with the Air product. * Traveler A is associated with the Hotel and Activity products. * Traveler C is associated with the Car product. * Traveler B is associated with the Rail product.
    @JsonProperty("travelers_references")
    @field:Size(min = 1, max = 40)
    @field:Valid
    override val travelersReferences: kotlin.collections.List<kotlin.String>? = null,
    // The attribute serves as a boolean indicator that significantly influences the handling of payment information during the fraud prevention process: * When 'pay_later' is set to 'true':   - This configuration signals that payment information is optional for the booking. Travelers are given the choice to defer payment until they arrive at the rental counter following the completion of the booking.   - It is imperative for partners to explicitly set this attribute to 'true' when payment information can be optional for a particular booking scenario. * When 'pay_later' is set to 'false':   - In this mode, the attribute mandates the inclusion of payment information during the order purchase screen request. Travelers are required to provide payment details.   - Partners must exercise caution and ensure they supply the necessary payment information, as failure to do so in cases where 'pay_later' is set to 'false' will result in a 'Bad Request' error. This error helps maintain the consistency and accuracy of the fraud prevention process and payment handling.
    @JsonProperty("pay_later")
    @field:Valid
    override val payLater: kotlin.Boolean? = null,
    // Identifies if the product amount is refundable on cancel: * When 'refundable' is set to 'true':   - This configuration signals that the booking is eligible for cancellation and refund according to the terms and conditions specified by the partner. Travelers are granted the option to cancel the booking and receive a refund, provided they meet the conditions outlined by the partner (e.g., cancellation within a specified time frame).   - Partners must ensure that they correctly configure this attribute to 'true' when a booking can be refunded, in order to avoid confusion or disputes regarding refund eligibility. * When 'refundable' is set to 'false':   - In this mode, the booking is non-refundable, and no refunds will be issued under any circumstances, except where required by law or other overriding regulations. Travelers should be clearly informed at the time of booking that the reservation is non-refundable.   - Partners should be careful when setting this attribute to 'false' to ensure that travelers are aware of the non-refundable policy, as failing to properly communicate this could lead to customer dissatisfaction or complaints.
    @JsonProperty("refundable")
    @field:Valid
    override val refundable: kotlin.Boolean? = null,
    // Identifies the type of air trip based on the air destinations.
    @JsonProperty("flight_type")
    val flightType: Air.FlightType? = null,
    // Airline booking confirmation code for the trip.
    @JsonProperty("passenger_name_record")
    @field:Length(max = 100)
    @field:Valid
    val passengerNameRecord: kotlin.String? = null,
    // Associated with Passenger Name Record (PNR).
    @JsonProperty("global_distribution_system_type")
    @field:Length(max = 100)
    @field:Valid
    val globalDistributionSystemType: kotlin.String? = null
) : TravelProduct {
    @JsonProperty("type")
    override val type: TravelProductType = TravelProductType.AIR

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var price: Amount? = null,
        private var inventoryType: kotlin.String? = null,
        private var inventorySource: TravelProduct.InventorySource? = null,
        private var departureTime: java.time.OffsetDateTime? = null,
        private var arrivalTime: java.time.OffsetDateTime? = null,
        private var airSegments: kotlin.collections.List<AirSegment>? = null,
        private var travelersReferences: kotlin.collections.List<kotlin.String>? = null,
        private var payLater: kotlin.Boolean? = null,
        private var refundable: kotlin.Boolean? = null,
        private var flightType: Air.FlightType? = null,
        private var passengerNameRecord: kotlin.String? = null,
        private var globalDistributionSystemType: kotlin.String? = null
    ) {
        fun price(price: Amount) = apply { this.price = price }

        fun inventoryType(inventoryType: kotlin.String) = apply { this.inventoryType = inventoryType }

        fun inventorySource(inventorySource: TravelProduct.InventorySource) = apply { this.inventorySource = inventorySource }

        fun departureTime(departureTime: java.time.OffsetDateTime) = apply { this.departureTime = departureTime }

        fun arrivalTime(arrivalTime: java.time.OffsetDateTime) = apply { this.arrivalTime = arrivalTime }

        fun airSegments(airSegments: kotlin.collections.List<AirSegment>) = apply { this.airSegments = airSegments }

        fun travelersReferences(travelersReferences: kotlin.collections.List<kotlin.String>?) = apply { this.travelersReferences = travelersReferences }

        fun payLater(payLater: kotlin.Boolean?) = apply { this.payLater = payLater }

        fun refundable(refundable: kotlin.Boolean?) = apply { this.refundable = refundable }

        fun flightType(flightType: Air.FlightType?) = apply { this.flightType = flightType }

        fun passengerNameRecord(passengerNameRecord: kotlin.String?) = apply { this.passengerNameRecord = passengerNameRecord }

        fun globalDistributionSystemType(globalDistributionSystemType: kotlin.String?) = apply { this.globalDistributionSystemType = globalDistributionSystemType }

        fun build(): Air {
            val instance =
                Air(
                    price = price!!,
                    inventoryType = inventoryType!!,
                    inventorySource = inventorySource!!,
                    departureTime = departureTime!!,
                    arrivalTime = arrivalTime!!,
                    airSegments = airSegments!!,
                    travelersReferences = travelersReferences,
                    payLater = payLater,
                    refundable = refundable,
                    flightType = flightType,
                    passengerNameRecord = passengerNameRecord,
                    globalDistributionSystemType = globalDistributionSystemType
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: Air) {
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
            price = price!!,
            inventoryType = inventoryType!!,
            inventorySource = inventorySource!!,
            departureTime = departureTime!!,
            arrivalTime = arrivalTime!!,
            airSegments = airSegments!!,
            travelersReferences = travelersReferences,
            payLater = payLater,
            refundable = refundable,
            flightType = flightType,
            passengerNameRecord = passengerNameRecord,
            globalDistributionSystemType = globalDistributionSystemType
        )

    /**
     * Identifies the type of air trip based on the air destinations.
     * Values: ROUNDTRIP,ONEWAY,MULTIPLE_DESTINATION
     */
    enum class FlightType(val value: kotlin.String) {
        @JsonProperty("ROUNDTRIP")
        ROUNDTRIP("ROUNDTRIP"),

        @JsonProperty("ONEWAY")
        ONEWAY("ONEWAY"),

        @JsonProperty("MULTIPLE_DESTINATION")
        MULTIPLE_DESTINATION("MULTIPLE_DESTINATION")
    }
}
