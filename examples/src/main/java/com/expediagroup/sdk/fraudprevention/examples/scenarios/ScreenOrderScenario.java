/*
 * Copyright 2024 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expediagroup.sdk.fraudprevention.examples.scenarios;

import com.expediagroup.sdk.fraudprevention.examples.services.FraudPreventionService;
import com.expediagroup.sdk.fraudpreventionv2.client.FraudPreventionV2Client;
import com.expediagroup.sdk.fraudpreventionv2.models.Amount;
import com.expediagroup.sdk.fraudpreventionv2.models.Car;
import com.expediagroup.sdk.fraudpreventionv2.models.CreditCard;
import com.expediagroup.sdk.fraudpreventionv2.models.CustomerAccount;
import com.expediagroup.sdk.fraudpreventionv2.models.DeviceDetails;
import com.expediagroup.sdk.fraudpreventionv2.models.Name;
import com.expediagroup.sdk.fraudpreventionv2.models.OrderPurchaseScreenRequest;
import com.expediagroup.sdk.fraudpreventionv2.models.OrderPurchaseScreenResponse;
import com.expediagroup.sdk.fraudpreventionv2.models.OrderPurchaseTransaction;
import com.expediagroup.sdk.fraudpreventionv2.models.Payment;
import com.expediagroup.sdk.fraudpreventionv2.models.PaymentBillingAddress;
import com.expediagroup.sdk.fraudpreventionv2.models.SiteInfo;
import com.expediagroup.sdk.fraudpreventionv2.models.Telephone;
import com.expediagroup.sdk.fraudpreventionv2.models.TelephoneType;
import com.expediagroup.sdk.fraudpreventionv2.models.TransactionDetails;
import com.expediagroup.sdk.fraudpreventionv2.models.TravelProduct;
import com.expediagroup.sdk.fraudpreventionv2.models.Traveler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public class ScreenOrderScenario implements FraudPreventionScenario {

    @Override
    public void run() {

        FraudPreventionV2Client fraudPreventionClient = FraudPreventionService.getFraudPreventionV2Client();

        SiteInfo siteInfo =
                SiteInfo.builder()
                        .countryCode("USA")
                        .agentAssisted(false)
                        .build();

        DeviceDetails deviceDetails =
                DeviceDetails.builder()
                        .deviceBox("TrustWidget")
                        .ipAddress("1.1.1.1")
                        .build();

        CustomerAccount customerAccount =
                CustomerAccount.builder()
                        .accountType(CustomerAccount.AccountType.STANDARD)
                        .name(
                                Name.builder()
                                        .firstName("John")
                                        .lastName("Smith")
                                        .build()
                        ).emailAddress("test@example.com")
                        .build();

        List<Telephone> telephones =
                Arrays.asList(
                        Telephone.builder()
                                .type(TelephoneType.HOME)
                                .countryAccessCode("1")
                                .areaCode("962")
                                .phoneNumber("1234567")
                                .build()
                );

        OffsetDateTime birthDate =
                OffsetDateTime.of(
                        LocalDate.of(1970, 4, 8),
                        LocalTime.of(8, 0),
                        ZoneOffset.of("-07:00")
                );

        List<Traveler> travelers =
                Arrays.asList(
                        Traveler.builder()
                                .emailAddress("imsafe@example.com")
                                .primary(true)
                                .telephones(telephones)
                                .travelerName(
                                        Name.builder()
                                                .firstName("Sally")
                                                .lastName("Brown")
                                                .build()
                                ).birthDate(birthDate)
                                .build()
                );

        Amount price =
                Amount.builder()
                        .currencyCode("USD")
                        .value(100)
                        .build();

        OffsetDateTime returnTime =
                OffsetDateTime.of(
                        LocalDate.of(2023, 1, 8),
                        LocalTime.of(7, 30),
                        ZoneOffset.of("-07:00")
                );

        OffsetDateTime pickupTime =
                OffsetDateTime.of(
                        LocalDate.of(2023, 1, 18),
                        LocalTime.of(8, 0),
                        ZoneOffset.of("-07:00")
                );

        List<TravelProduct> travelProducts =
                Arrays.asList(
                        Car.builder()
                                .inventoryType("Agency")
                                .inventorySource(TravelProduct.InventorySource.AGENCY)
                                .travelersReferences(Arrays.asList("Reference"))
                                .price(price)
                                .pickUpLocation("pick up location")
                                .dropOffLocation("drop off location")
                                .returnTime(returnTime)
                                .pickupTime(pickupTime)
                                .build()
                );

        OffsetDateTime expiryDate =
                OffsetDateTime.of(
                        LocalDate.of(2027, 4, 8),
                        LocalTime.of(8, 0),
                        ZoneOffset.of("-07:00")
                );

        List<CreditCard> payments =
                Arrays.asList(
                        CreditCard.builder()
                                .billingName(
                                        Name.builder()
                                                .firstName("Sally")
                                                .lastName("Brown")
                                                .build()
                                ).brand(Payment.Brand.VISA)
                                .authorizedAmount(
                                        Amount.builder()
                                                .value(100)
                                                .currencyCode("USD")
                                                .build()
                                ).verifiedAmount(
                                        Amount.builder()
                                                .value(123)
                                                .currencyCode("USD")
                                                .build()
                                ).cardAvsResponse("Z")
                                .cardCvvResponse("0")
                                .billingEmailAddress("sally@example.com")
                                .cardNumber("4111111111111111")
                                .telephones(telephones)
                                .cardType(CreditCard.CardType.MASTER_CARD)
                                .expiryDate(expiryDate)
                                .billingAddress(
                                        PaymentBillingAddress.builder()
                                                .zipCode("11183")
                                                .addressLine1("address")
                                                .city("city")
                                                .countryCode("USA")
                                                .build()
                                ).build()
                );

        TransactionDetails transactionDetails =
                TransactionDetails.builder()
                        .orderId("33322220004")
                        .currentOrderStatus(TransactionDetails.CurrentOrderStatus.IN_PROGRESS)
                        .orderType(TransactionDetails.OrderType.CREATE)
                        .travelers(travelers)
                        .travelProducts(travelProducts)
                        .payments(payments)
                        .build();

        OrderPurchaseTransaction transaction =
                OrderPurchaseTransaction.builder()
                        .siteInfo(siteInfo)
                        .deviceDetails(deviceDetails)
                        .customerAccount(customerAccount)
                        .transactionDetails(transactionDetails)
                        .build();

        OrderPurchaseScreenRequest request =
                OrderPurchaseScreenRequest.builder()
                        .transaction(transaction)
                        .build();

        OrderPurchaseScreenResponse response = fraudPreventionClient.screenOrder(request);

    }
}
