package com.revobank.accounts.core.transfers;

import com.revobank.accounts.BaseIntegrationTest;
import com.revobank.accounts.core.accounts.Account;
import com.revobank.accounts.core.accounts.AccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TransferServiceTest extends BaseIntegrationTest {
    @Autowired
    private TransferService transferService;

    @Test
    public void debitTransfer() {
        Transfer expected = Transfer.builder()
                .fromAccount(Account.builder().id(1L).build())
                .beneficiaryAccount(Account.builder().id(2L).build())
                .amount(new BigDecimal(5))
                .type("Debit")
                .build();

        transferService.transfer(expected);
        Transfer actual = transferService.findAll().get(2);

        assertEquals(3L, actual.getId());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(new BigDecimal("35"), actual.getFromAccount().getAvailableAmount());
        assertEquals(new BigDecimal("105"), actual.getBeneficiaryAccount().getAvailableAmount());
    }

    @Test
    public void creditTransfer() {
        Transfer expected = Transfer.builder()
                .fromAccount(Account.builder().id(1L).build())
                .beneficiaryAccount(Account.builder().id(2L).build())
                .amount(new BigDecimal("5"))
                .type("Credit")
                .build();

        transferService.transfer(expected);
        Transfer actual = transferService.findAll().get(2);


        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(new BigDecimal("45"), actual.getFromAccount().getAvailableAmount());
        assertEquals(new BigDecimal("95"), actual.getBeneficiaryAccount().getAvailableAmount());
    }

    @Test
    public void getById() {
        Transfer actual = transferService.get(1L);

        assertEquals("Debit", actual.getType());
        assertEquals(new BigDecimal("5"), actual.getAmount());
        assertEquals(new BigDecimal("100"), actual.getFromAccount().getAvailableAmount());
        assertEquals(new BigDecimal("40"), actual.getBeneficiaryAccount().getAvailableAmount());
    }

    @Test
    public void getNotExistId() {
        Transfer actual = transferService.get(666L);

        assertNull(actual.getId());
    }

    @Test
    public void creditWithNegativeBalance() {
        Transfer expected = Transfer.builder()
                .fromAccount(Account.builder().id(1L).build())
                .beneficiaryAccount(Account.builder().id(2L).build())
                .amount(new BigDecimal("500"))
                .type("Credit")
                .build();

        NegativeBalanceException negativeBalanceException = assertThrows(NegativeBalanceException.class,
                () -> {
                    transferService.transfer(expected);
                });

        assertEquals("Balance can't be negative", negativeBalanceException.getMessage());
    }

    @Test
    public void debitWithNegativeBalance() {
        Transfer expected = Transfer.builder()
                .fromAccount(Account.builder().id(1L).build())
                .beneficiaryAccount(Account.builder().id(2L).build())
                .amount(new BigDecimal("500"))
                .type("Debit")
                .build();

        NegativeBalanceException negativeBalanceException = assertThrows(NegativeBalanceException.class,
                () -> {
                    transferService.transfer(expected);
                });

        assertEquals("Balance can't be negative", negativeBalanceException.getMessage());
    }

    @Test
    public void transferWithNotExistingAccount() {
        Transfer expected = Transfer.builder()
                .fromAccount(Account.builder().id(666L).build())
                .beneficiaryAccount(Account.builder().id(661L).build())
                .build();

        AccountNotFoundException accountNotFoundException = assertThrows(AccountNotFoundException.class,
                () -> {
                    transferService.transfer(expected);
                });

        assertEquals("Account/s not found/active", accountNotFoundException.getMessage());
    }

    @Test
    public void transferWithNotExistingPaymentType() {
        Transfer expected = Transfer.builder()
                .fromAccount(Account.builder().id(1L).build())
                .beneficiaryAccount(Account.builder().id(2L).build())
                .amount(new BigDecimal("5"))
                .type("NotExistType")
                .build();

        NotExistPaymentTypeException notExistPaymentTypeException = assertThrows(NotExistPaymentTypeException.class,
                () -> {
                    transferService.transfer(expected);
                });

        assertEquals("Transfer type not found", notExistPaymentTypeException.getMessage());
    }

    private LocalDateTime newDate(String expectedCreated) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(expectedCreated, formatter);
    }
}