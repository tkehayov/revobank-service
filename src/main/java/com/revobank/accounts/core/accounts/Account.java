package com.revobank.accounts.core.accounts;

import com.revobank.accounts.repositories.accounts.AccountEntity;
import com.revobank.accounts.rest.accounts.AccountCreationRequestDto;
import com.revobank.accounts.rest.accounts.AccountResponseDto;
import com.revobank.accounts.rest.accounts.AccountUpdateDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Account {
    private Long id;
    private String name;
    private String iban;
    @Builder.Default
    private Boolean status = false;
    @Builder.Default
    private BigDecimal availableAmount = BigDecimal.ZERO;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modified = LocalDateTime.now();

    public static AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .name(account.getName())
                .iban(account.getIban())
                .availableAmount(account.getAvailableAmount())
                .status(account.getStatus())
                .created(account.getCreated())
                .modified(account.getModified())
                .build();
    }
    public static AccountEntity toEntityWithId(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .name(account.getName())
                .iban(account.getIban())
                .availableAmount(account.getAvailableAmount())
                .status(account.getStatus())
                .created(account.getCreated())
                .modified(account.getModified())
                .build();
    }

    public static Account toDomain(AccountCreationRequestDto accountDto) {
        return Account.builder()
                .created(LocalDateTime.now())
                .name(accountDto.getName())
                .iban(accountDto.getIban())
                .build();
    }

    public static Account toDomain(AccountEntity accountEntity) {
        return Account.builder()
                .id(accountEntity.getId())
                .name(accountEntity.getName())
                .iban(accountEntity.getIban())
                .created(accountEntity.getCreated())
                .modified(accountEntity.getModified())
                .availableAmount(accountEntity.getAvailableAmount())
                .status(accountEntity.getStatus())
                .build();
    }

    public static List<Account> toDomain(List<AccountEntity> accountEntities) {
        return accountEntities.stream()
                .map(account -> Account.builder()
                        .id(account.getId())
                        .name(account.getName())
                        .iban(account.getIban())
                        .created(account.getCreated())
                        .modified(account.getModified())
                        .availableAmount(account.getAvailableAmount())
                        .status(account.getStatus())
                        .build())
                .toList();
    }

    public static List<AccountResponseDto> toDto(List<Account> accounts) {
        return accounts.stream()
                .map(account -> AccountResponseDto.builder()
                        .id(account.getId())
                        .name(account.getName())
                        .iban(account.getIban())
                        .created(account.getCreated())
                        .modified(account.getModified())
                        .availableAmount(account.getAvailableAmount())
                        .status(account.getStatus())
                        .build())
                .toList();
    }

    public static AccountResponseDto toDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .name(account.getName())
                .iban(account.getIban())
                .created(account.getCreated())
                .modified(account.getModified())
                .availableAmount(account.getAvailableAmount())
                .status(account.getStatus())
                .build();
    }


    public static Account toDomain(AccountUpdateDto accountUpdateDto) {
        return Account.builder()
                .id(accountUpdateDto.getId())
                .name(accountUpdateDto.getName())
                .iban(accountUpdateDto.getIban())
                .created(accountUpdateDto.getCreated())
                .modified(accountUpdateDto.getModified())
                .availableAmount(accountUpdateDto.getAvailableAmount())
                .status(accountUpdateDto.getStatus())
                .build();
    }
    public static Account toDomainUpdate(AccountUpdateDto accountUpdateDto) {
        return Account.builder()
                .id(accountUpdateDto.getId())
                .name(accountUpdateDto.getName())
                .iban(accountUpdateDto.getIban())
                .availableAmount(accountUpdateDto.getAvailableAmount())
                .status(accountUpdateDto.getStatus())
                .build();
    }
}
