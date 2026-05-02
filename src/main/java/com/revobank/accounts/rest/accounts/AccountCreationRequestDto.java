package com.revobank.accounts.rest.accounts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccountCreationRequestDto {
    @NotBlank(message = "name is mandatory")
    @NotNull(message = "name cannot be empty")
    private final String name;

    @NotBlank(message = "iban is mandatory")
    @NotNull(message = "iban cannot be empty")
    @Size(max = 34, message = "Size must be max 34")
    private final String iban;
}
