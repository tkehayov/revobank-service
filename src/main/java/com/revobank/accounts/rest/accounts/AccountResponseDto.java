package com.revobank.accounts.rest.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class AccountResponseDto {
    private Long id;
    private String name;
    private String iban;
    private Boolean status;
    private BigDecimal availableAmount;
    private LocalDateTime created;
    private LocalDateTime modified;
}
