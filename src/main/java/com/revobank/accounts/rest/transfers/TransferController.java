package com.revobank.accounts.rest.transfers;

import com.revobank.accounts.core.transfers.Transfer;
import com.revobank.accounts.core.transfers.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transfers")
public class TransferController {
    private final TransferService service;

    @PostMapping
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        Transfer transfer = Transfer.toDomain(transferRequestDto);
        service.transfer(transfer);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TransferDto>> list() {
        List<Transfer> all = service.findAll();
        List<TransferDto> dto = Transfer.toDto(all);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferDto> get(@PathVariable Long id) {
        Transfer transfer = service.get(id);
        TransferDto dto = Transfer.toDto(transfer);

        return ResponseEntity.ok().body(dto);
    }
}
