package com.revobank.accounts.core.accounts;

import com.revobank.accounts.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AccountServiceTest extends BaseIntegrationTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void createAndGetAccount() {
        LocalDateTime now = LocalDateTime.now();

        Account expected = Account.builder()
                .name("George")
                .iban("NL12STSA93000012245612")
                .availableAmount(new BigDecimal("100"))
                .status(true)
                .created(now)
                .modified(now)
                .build();

        accountService.save(expected);
        Account actual = accountService.get(3L);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getIban(), actual.getIban());
        assertEquals(expected.getAvailableAmount(), actual.getAvailableAmount());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getCreated(), actual.getCreated());
        assertEquals(expected.getModified(), actual.getModified());
    }

    @Test
    public void findAll() {
        List<Account> all = accountService.findAll();
        Account actual = all.get(1);
        LocalDateTime expectedCreated = newDate("2026-05-01 19:30:08.323");
        LocalDateTime expectedModified = newDate("2026-05-02 10:38:52.787");

        assertEquals(2, all.size());
        assertEquals(2, actual.getId());
        assertEquals("Peter", actual.getName());
        assertEquals("BG12STSA93000012245611", actual.getIban());
        assertEquals(expectedCreated, actual.getCreated());
        assertEquals(expectedModified, actual.getModified());
        assertEquals(new BigDecimal(100), actual.getAvailableAmount());
        assertEquals(true, actual.getStatus());
    }

    @Test
    public void updateStatus() {
        LocalDateTime expectedTime = LocalDateTime.of(2026, 5, 1, 12, 30);

        accountService.updateStatus(1L, false, expectedTime);
        Account actual = accountService.get(1L);

        assertEquals(false, actual.getStatus());
        assertEquals(expectedTime, actual.getModified());
    }

    @Test
    public void getNotExistingAccount() {
        AccountNotFoundException accountNotFoundException = assertThrows(AccountNotFoundException.class,
                () -> {
                    accountService.get(666L);
                });

        assertEquals("User with id 666 not found", accountNotFoundException.getMessage());
    }

    @Test
    public void update() {
        Account expected = Account.builder()
                .id(1L)
                .name("PeterUpdated")
                .build();

        accountService.update(expected);
        Account actual = accountService.get(1L);

        assertEquals(expected.getName(), actual.getName());
    }

    private LocalDateTime newDate(String expectedCreated) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(expectedCreated, formatter);
    }
}