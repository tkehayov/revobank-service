package com.revobank.accounts.core.transfers;

import com.revobank.accounts.core.accounts.Account;
import com.revobank.accounts.core.accounts.AccountNotFoundException;
import com.revobank.accounts.repositories.accounts.AccountEntity;
import com.revobank.accounts.repositories.accounts.AccountRepository;
import com.revobank.accounts.repositories.transfers.TransferEntity;
import com.revobank.accounts.repositories.transfers.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final Map<String, TransferStrategy> strategies;

    public void transfer(Transfer transfer) {
        Optional<AccountEntity> fromAccount = accountRepository.findByIdAndStatusIsTrue(transfer.getFromAccount().getId());
        Optional<AccountEntity> beneficiaryAccount = accountRepository.findByIdAndStatusIsTrue(transfer.getBeneficiaryAccount().getId());

        if (accountIsEmpty(fromAccount, beneficiaryAccount)) {
            throw new AccountNotFoundException("Account/s not found/active");
        }
        AccountEntity fromAccountEntity = fromAccount.get();
        AccountEntity beneficiaryAccountEntity = beneficiaryAccount.get();
        BigDecimal availableAmountFrom = fromAccountEntity.getAvailableAmount();
        BigDecimal availableAmountBeneficiary = beneficiaryAccountEntity.getAvailableAmount();

        TransferStrategy transferStrategy = strategies.get(transfer.getType());
        BigDecimal calculatedAvailableAmountFrom = transferStrategy.executeFromCalculation(availableAmountFrom, transfer.getAmount());
        BigDecimal calculatedAvailableAmountBeneficient = transferStrategy.executeBeneficientCalculation(availableAmountBeneficiary, transfer.getAmount());

        fromAccountEntity.setAvailableAmount(calculatedAvailableAmountFrom);
        fromAccountEntity.setModified(LocalDateTime.now());
        beneficiaryAccountEntity.setAvailableAmount(calculatedAvailableAmountBeneficient);
        beneficiaryAccountEntity.setModified(LocalDateTime.now());

        TransferEntity transferEntity = TransferEntity.builder()
                .account(fromAccountEntity)
                .beneficiaryAccount(beneficiaryAccount.get())
                .amount(transfer.getAmount())
                .type(transfer.getType())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .build();

        transferRepository.save(transferEntity);
    }


    public List<Transfer> findAll() {
        List<TransferEntity> transferEntities = transferRepository.findAll();

        return Transfer.toDomain(transferEntities);
    }


    public Transfer get(Long id) {
        Optional<TransferEntity> transferEntity = transferRepository.findById(id);

        if (transferEntity.isEmpty()) {
            return Transfer.builder().build();
        }

        return Transfer.toDomain(transferEntity.get());
    }

    private boolean accountIsEmpty(Optional<AccountEntity> fromAccount, Optional<AccountEntity> beneficiaryAccount) {
        return fromAccount.isEmpty() || beneficiaryAccount.isEmpty();
    }
}
