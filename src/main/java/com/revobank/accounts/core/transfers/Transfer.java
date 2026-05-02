package com.revobank.accounts.core.transfers;

import com.revobank.accounts.core.accounts.Account;
import com.revobank.accounts.repositories.transfers.TransferEntity;
import com.revobank.accounts.rest.transfers.TransferDto;
import com.revobank.accounts.rest.transfers.TransferRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Transfer {
    private Long id;
    private Account fromAccount;
    private Account beneficiaryAccount;
    private BigDecimal amount;
    private String type;
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modified = LocalDateTime.now();

    public static Transfer toDomain(TransferRequestDto transferRequestDto) {
        return Transfer.builder()
                .fromAccount(Account.builder().id(transferRequestDto.getFromAccountId()).build())
                .beneficiaryAccount(Account.builder().id(transferRequestDto.getBeneficiaryId()).build())
                .type(transferRequestDto.getType())
                .amount(transferRequestDto.getAmount())
                .build();
    }

    public static Transfer toDomain(TransferEntity transferEntity) {
        return Transfer.builder()
                .id(transferEntity.getId())
                .fromAccount(Account.toDomain(transferEntity.getAccount()))
                .beneficiaryAccount(Account.toDomain(transferEntity.getBeneficiaryAccount()))
                .amount(transferEntity.getAmount())
                .type(transferEntity.getType())
                .created(transferEntity.getCreated())
                .modified(transferEntity.getModified())
                .build();
    }

    public static List<Transfer> toDomain(List<TransferEntity> transferEntities) {
        return transferEntities.stream().map(entity ->
                        Transfer.builder()
                                .id(entity.getId())
                                .fromAccount(Account.toDomain(entity.getAccount()))
                                .beneficiaryAccount(Account.toDomain(entity.getBeneficiaryAccount()))
                                .amount(entity.getAmount())
                                .type(entity.getType())
                                .created(entity.getCreated())
                                .modified(entity.getModified())
                                .build())
                .toList();
    }

    public static List<TransferDto> toDto(List<Transfer> transfers) {
        return transfers.stream().map(transfer ->
                        TransferDto.builder()
                                .id(transfer.getId())
                                .fromAccount(transfer.getFromAccount())
                                .beneficiaryAccount(transfer.getBeneficiaryAccount())
                                .amount(transfer.getAmount())
                                .type(transfer.getType())
                                .created(transfer.getCreated())
                                .modified(transfer.getModified())
                                .build())
                .toList();
    }

    public static TransferDto toDto(Transfer transfer) {
        return TransferDto.builder()
                .id(transfer.getId())
                .fromAccount(transfer.getFromAccount())
                .beneficiaryAccount(transfer.getBeneficiaryAccount())
                .amount(transfer.getAmount())
                .type(transfer.getType())
                .created(transfer.getCreated())
                .modified(transfer.getModified())
                .build();
    }
}
