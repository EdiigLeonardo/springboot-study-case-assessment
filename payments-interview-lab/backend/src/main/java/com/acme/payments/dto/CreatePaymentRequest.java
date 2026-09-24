package com.acme.payments.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record CreatePaymentRequest(
    @NotBlank String externalReference,
    @NotBlank String merchantId,
    @NotBlank String payerIban,
    @NotBlank String payeeIban,
    @DecimalMin("0.01") BigDecimal amount
) {}
