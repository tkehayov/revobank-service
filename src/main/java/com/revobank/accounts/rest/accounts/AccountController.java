package com.revobank.accounts.rest.accounts;

import com.revobank.accounts.core.accounts.Account;
import com.revobank.accounts.core.accounts.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
public class AccountController {
    private final AccountService service;

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody AccountCreationRequestDto accountCreationRequestDto
    ) {
        Account account = Account.toDomain(accountCreationRequestDto);
        service.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<Account> all = service.findAll();
        List<AccountResponseDto> dto = Account.toDto(all);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Account account = service.get(id);
        AccountResponseDto dto = Account.toDto(account);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping
    public ResponseEntity<?> update(
                                    @RequestBody AccountUpdateDto accountUpdateDto) {
        Account account = Account.toDomainUpdate(accountUpdateDto);
        service.update(account);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody StatusDto status) {
        LocalDateTime modifiedDate = LocalDateTime.now();
        service.updateStatus(id, status.getStatus(), modifiedDate);

        return ResponseEntity.ok().build();
    }
}
