package com.revobank.accounts.rest.accounts;

import com.revobank.accounts.repositories.accounts.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class AccountUpdateDto {
    private Long id;
    private String name;
    private String iban;
    private Boolean status;
    private BigDecimal availableAmount;
    private LocalDateTime created;
    private LocalDateTime modified;


}
