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
import com.expediagroup.sdk.fraudpreventionv2.models.Amount
import com.expediagroup.sdk.fraudpreventionv2.models.Name
import com.expediagroup.sdk.fraudpreventionv2.models.Operations
import com.expediagroup.sdk.fraudpreventionv2.models.Payment
import com.expediagroup.sdk.fraudpreventionv2.models.PaymentBillingAddress
import com.expediagroup.sdk.fraudpreventionv2.models.PaymentMethod
import com.expediagroup.sdk.fraudpreventionv2.models.PaymentReason
import com.expediagroup.sdk.fraudpreventionv2.models.PaymentThreeDSCriteria
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator
import javax.validation.Valid
import javax.validation.Validation
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 *
 * @param cardNumber Gift card number.
 * @param cardHolderName The name of gift card holder.
 * @param pin The PIN of gift card.
 */
data class GiftCard(
    // The `brand` field value is the payment brand used for payment on this transaction. For credit card payment method ensure attributes mentioned in dictionary below are set to corresponding values only. Ensure to comply with the naming standards provided in below dictionary. For example, some Payment processors use “Japan Credit Bureau” but “JCB” should be used when calling Fraud API. Incorrect `brand` - `card_type` combination will result in data quality issues and result in degraded risk recommendation. 'brand' is an enum value with the following mapping with CreditCard 'card_type' attribute: *       brand                 :      card_type * ------------------------------------------------------- * `AMERICAN_EXPRESS`          : `AMERICAN_EXPRESS` * `DINERS_CLUB_INTERNATIONAL` : `DINERS_CLUB` * `BC_CARD`                   : `DINERS_CLUB` * `DISCOVER`                  : `DISCOVER` * `BC_CARD`                   : `DISCOVER` * `DINERS_CLUB_INTERNATIONAL` : `DISCOVER` * `JCB`                       : `DISCOVER` * `JCB`                       : `JCB` * `MASTER_CARD`               : `MASTER_CARD` * `MAESTRO`                   : `MASTER_CARD` * `POSTEPAY_MASTERCARD`       : `MASTER_CARD` * `SOLO`                      : `SOLO` * `SWITCH`                    : `SWITCH` * `MAESTRO`                   : `MAESTRO` * `CHINA_UNION_PAY`           : `CHINA_UNION_PAY` * `UATP`                      : `UATP` * `UATP_SUPPLY`               : `UATP` * `AIR_PLUS`                  : `UATP` * `UA_PASS_PLUS`              : `UATP` * `VISA`                      : `VISA` * `VISA_DELTA`                : `VISA` * `VISA_ELECTRON`             : `VISA` * `CARTA_SI`                  : `VISA` * `CARTE_BLEUE`               : `VISA` * `VISA_DANKORT`              : `VISA` * `POSTEPAY_VISA_ELECTRON`    : `VISA` * `PAYPAL`                    :  'brand' with 'Points' payment_type is an enum value with following: * `EXPEDIA_REWARDS` * `AMEX_POINTS` * `BANK_OF_AMERICA_REWARDS` * `DISCOVER_POINTS` * `MASTER_CARD_POINTS` * `CITI_THANK_YOU_POINTS` * `MERRILL_LYNCH_REWARDS` * `WELLS_FARGO_POINTS` * `DELTA_SKY_MILES` * `UNITED_POINTS` * `DISCOVER_MILES` * `ALASKA_MILES` * `RBC_REWARDS` * `BILT_REWARDS` * `ORBUCKS` * `CHEAP_CASH` * `BONUS_PLUS` * `ULTIMATE_REWARDS`  'brand' with 'GiftCard' payment_type is an enum value with following: * `GIFT_CARD`  'brand' with 'InternetBankPayment' payment_type is an enum value with following: * `IBP` * `LOCAL_DEBIT_CARD` * `SOFORT` * `YANDEX` * `WEB_MONEY` * `QIWI` * `BITCOIN`  'brand' with 'DirectDebit' payment_type is an enum value with following: * `ELV` * `INTER_COMPANY` * `SEPA_ELV`  'brand' with 'ThirdPartyProvider' payment_type is an enum value with following: * `STRIPE`
    @JsonProperty("brand")
    @field:NotNull
    override val brand: Payment.Brand,
    @JsonProperty("billing_name")
    @field:NotNull
    @field:Valid
    override val billingName: Name,
    @JsonProperty("billing_address")
    @field:NotNull
    @field:Valid
    override val billingAddress: PaymentBillingAddress,
    // Email address associated with the payment.
    @JsonProperty("billing_email_address")
    @field:Length(max = 200)
    @field:NotNull
    @field:Valid
    override val billingEmailAddress: kotlin.String,
    // Gift card number.
    @JsonProperty("card_number")
    @field:Pattern(regexp = "^[0-9A-Za-z]{4,16}$")
    @field:Length(max = 16)
    @field:NotNull
    @field:Valid
    val cardNumber: kotlin.String,
    // The name of gift card holder.
    @JsonProperty("card_holder_name")
    @field:Length(max = 200)
    @field:NotNull
    @field:Valid
    val cardHolderName: kotlin.String,
    // The PIN of gift card.
    @JsonProperty("pin")
    @field:Pattern(regexp = "^[0-9]{4,8}$")
    @field:Length(max = 8)
    @field:NotNull
    @field:Valid
    val pin: kotlin.String,
    @JsonProperty("reason")
    @field:Valid
    override val reason: PaymentReason? = null,
    @JsonProperty("authorized_amount")
    @field:Valid
    override val authorizedAmount: Amount? = null,
    @JsonProperty("verified_amount")
    @field:Valid
    override val verifiedAmount: Amount? = null,
    @JsonProperty("three_digits_secure_criteria")
    @field:Valid
    override val threeDigitsSecureCriteria: PaymentThreeDSCriteria? = null,
    @JsonProperty("operations")
    @field:Valid
    override val operations: Operations? = null,
    // A key-value pair map to hold additional attributes.
    @JsonProperty("extensions")
    @field:Valid
    override val extensions: kotlin.collections.Map<kotlin.String, kotlin.String>? = null
) : Payment {
    @JsonProperty("method")
    override val method: PaymentMethod = PaymentMethod.GIFT_CARD

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder(
        private var brand: Payment.Brand? = null,
        private var billingName: Name? = null,
        private var billingAddress: PaymentBillingAddress? = null,
        private var billingEmailAddress: kotlin.String? = null,
        private var cardNumber: kotlin.String? = null,
        private var cardHolderName: kotlin.String? = null,
        private var pin: kotlin.String? = null,
        private var reason: PaymentReason? = null,
        private var authorizedAmount: Amount? = null,
        private var verifiedAmount: Amount? = null,
        private var threeDigitsSecureCriteria: PaymentThreeDSCriteria? = null,
        private var operations: Operations? = null,
        private var extensions: kotlin.collections.Map<kotlin.String, kotlin.String>? = null
    ) {
        fun brand(brand: Payment.Brand) = apply { this.brand = brand }

        fun billingName(billingName: Name) = apply { this.billingName = billingName }

        fun billingAddress(billingAddress: PaymentBillingAddress) = apply { this.billingAddress = billingAddress }

        fun billingEmailAddress(billingEmailAddress: kotlin.String) = apply { this.billingEmailAddress = billingEmailAddress }

        fun cardNumber(cardNumber: kotlin.String) = apply { this.cardNumber = cardNumber }

        fun cardHolderName(cardHolderName: kotlin.String) = apply { this.cardHolderName = cardHolderName }

        fun pin(pin: kotlin.String) = apply { this.pin = pin }

        fun reason(reason: PaymentReason?) = apply { this.reason = reason }

        fun authorizedAmount(authorizedAmount: Amount?) = apply { this.authorizedAmount = authorizedAmount }

        fun verifiedAmount(verifiedAmount: Amount?) = apply { this.verifiedAmount = verifiedAmount }

        fun threeDigitsSecureCriteria(threeDigitsSecureCriteria: PaymentThreeDSCriteria?) = apply { this.threeDigitsSecureCriteria = threeDigitsSecureCriteria }

        fun operations(operations: Operations?) = apply { this.operations = operations }

        fun extensions(extensions: kotlin.collections.Map<kotlin.String, kotlin.String>?) = apply { this.extensions = extensions }

        fun build(): GiftCard {
            val instance =
                GiftCard(
                    brand = brand!!,
                    billingName = billingName!!,
                    billingAddress = billingAddress!!,
                    billingEmailAddress = billingEmailAddress!!,
                    cardNumber = cardNumber!!,
                    cardHolderName = cardHolderName!!,
                    pin = pin!!,
                    reason = reason,
                    authorizedAmount = authorizedAmount,
                    verifiedAmount = verifiedAmount,
                    threeDigitsSecureCriteria = threeDigitsSecureCriteria,
                    operations = operations,
                    extensions = extensions
                )

            validate(instance)

            return instance
        }

        private fun validate(instance: GiftCard) {
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
            brand = brand!!,
            billingName = billingName!!,
            billingAddress = billingAddress!!,
            billingEmailAddress = billingEmailAddress!!,
            cardNumber = cardNumber!!,
            cardHolderName = cardHolderName!!,
            pin = pin!!,
            reason = reason,
            authorizedAmount = authorizedAmount,
            verifiedAmount = verifiedAmount,
            threeDigitsSecureCriteria = threeDigitsSecureCriteria,
            operations = operations,
            extensions = extensions
        )
}
