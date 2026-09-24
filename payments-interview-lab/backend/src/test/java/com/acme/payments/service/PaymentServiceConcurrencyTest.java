package com.acme.payments.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PaymentServiceConcurrencyTest {
    @Disabled("Exercise: implement an integration test that starts two creates with the same externalReference at the same time")
    @Test void duplicateReferenceRaceCondition() {}

    @Disabled("Exercise: implement concurrent transfers from the same account and prove balance invariants")
    @Test void concurrentBalanceUpdates() {}
}
