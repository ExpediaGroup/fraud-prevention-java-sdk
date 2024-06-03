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

package com.expediagroup.sdk.fraudprevention.examples.services;

import com.expediagroup.sdk.fraudprevention.examples.Constants;
import com.expediagroup.sdk.fraudpreventionv2.client.FraudPreventionV2Client;

public class FraudPreventionService {

    protected static final FraudPreventionV2Client fraudPreventionV2Client = FraudPreventionV2Client
            .builder()
            .key(System.getProperty("com.expediagroup.fraudpreventionsdkjava.apikey"))
            .secret(System.getProperty("com.expediagroup.fraudpreventionsdkjava.apisecret"))
            .endpoint(Constants.SANDBOX_URL) // remove to connect to the production environment
            .requestTimeout(10000)
            .build();

    public static FraudPreventionV2Client getFraudPreventionV2Client() {
        return fraudPreventionV2Client;
    }
}
