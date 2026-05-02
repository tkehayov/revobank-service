package com.revobank.accounts.core.transfers;

import java.math.BigDecimal;

public interface TransferStrategy {
    BigDecimal executeFromCalculation(BigDecimal balance, BigDecimal amount);

    BigDecimal executeBeneficientCalculation(BigDecimal balance, BigDecimal amount);
}
