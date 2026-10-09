package dev.bootcamp.core;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test void adds_same_currency() {
        var eur = Currency.getInstance("EUR");
        assertEquals(new Money(new BigDecimal("12.50"), eur),
                new Money(new BigDecimal("10.00"), eur).add(new Money(new BigDecimal("2.50"), eur)));
    }
    @Test void rejects_excess_scale() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(new BigDecimal("1.001"), Currency.getInstance("EUR")));
    }
}
