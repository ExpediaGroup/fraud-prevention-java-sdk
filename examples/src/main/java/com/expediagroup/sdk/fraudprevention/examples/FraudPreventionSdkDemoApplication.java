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

package com.expediagroup.sdk.fraudprevention.examples;

import com.expediagroup.sdk.fraudprevention.examples.scenarios.ScreenOrderScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FraudPreventionSdkDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(FraudPreventionSdkDemoApplication.class);

    public static void main(String[] args) {

        logger.info("==================================================================================================");
        logger.info("==================================================================================================");
        logger.info("==                                                                                              ==");
        logger.info("==         Howdy! This is a demonstration of Expedia Group Fraud Prevention SDK, Enjoy!         ==");
        logger.info("==                                                                                              ==");
        logger.info("==================================================================================================");
        logger.info("==================================================================================================");

        // Run the Screen Order Scenario
        logger.info("Running Screen Order Scenario...");
        ScreenOrderScenario screenOrderScenario = new ScreenOrderScenario();
        screenOrderScenario.run();

    }
}