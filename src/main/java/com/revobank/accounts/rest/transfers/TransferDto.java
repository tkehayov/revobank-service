package com.revobank.accounts.rest.transfers;

import com.revobank.accounts.core.accounts.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class TransferDto {
    private Long id;
    private Account fromAccount;
    private Account beneficiaryAccount;
    private BigDecimal amount;
    private String type;
    private LocalDateTime created;
    private LocalDateTime modified;
}

