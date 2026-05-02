package com.revobank.accounts.core.transfers;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("Credit")
public class CreditStrategy implements TransferStrategy {
    @Override
    public BigDecimal executeFromCalculation(BigDecimal availableBalance, BigDecimal amount) {
        return availableBalance.add(amount);
    }

    @Override
    public BigDecimal executeBeneficientCalculation(BigDecimal availableBalance, BigDecimal amount) {
        BigDecimal result = availableBalance.subtract(amount);

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalanceException("Balance can't be negative");
        }
        return result;
    }
}
