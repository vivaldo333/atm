package com.fuib.persistence.entities;

import java.math.BigDecimal;

public class AccountEntity {
    private String id;
    private BigDecimal balance;
    private BigDecimal actualBalance;
    private BigDecimal debtBalance;

    public AccountEntity(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
        this.actualBalance = balance;
        this.debtBalance = BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public BigDecimal getDebtBalance() {
        return debtBalance;
    }

    public void setDebtBalance(BigDecimal debtBalance) {
        this.debtBalance = debtBalance;
    }
}
