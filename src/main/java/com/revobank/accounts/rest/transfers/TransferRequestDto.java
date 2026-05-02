package com.revobank.accounts.rest.transfers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class TransferRequestDto {
    @NotNull(message = "From account id is required")
    private final Long fromAccountId;

    @NotNull(message = "Beneficiary account id is required")
    private final Long beneficiaryId;

    @NotBlank(message = "Transfer type is required")
    @NotNull(message = "Transfer type is required")
    private final String type;

    @NotNull(message = "Amount is required")
    private final BigDecimal amount;
}
