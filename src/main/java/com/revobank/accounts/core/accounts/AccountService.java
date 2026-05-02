package com.revobank.accounts.core.accounts;

import com.revobank.accounts.repositories.accounts.AccountEntity;
import com.revobank.accounts.repositories.accounts.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;

    public void save(Account account) {
        AccountEntity entity = Account.toEntity(account);

        repository.save(entity);
    }

    public List<Account> findAll() {
        List<AccountEntity> all = repository.findAll();

        return Account.toDomain(all);
    }

    @Transactional
    public void updateStatus(Long id, boolean status, LocalDateTime modifiedDate) {
        int total = repository.updateStatus(id, modifiedDate, status);

        if (total == 0) {
            throw new AccountNotFoundException("User with id " + id + " not found");
        }
    }

    public Account get(Long id) {
        AccountEntity accountEntity = repository.findById(id).
                orElseThrow(() ->
                        new AccountNotFoundException("User with id " + id + " not found")
                );

        return Account.toDomain(accountEntity);
    }

    public void update(Account account) {
        AccountEntity fullEntity = Account.toEntityWithId(account);

        repository.save(fullEntity);
    }
}
