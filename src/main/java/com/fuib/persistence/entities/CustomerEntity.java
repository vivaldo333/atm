package com.fuib.persistence.entities;

public class CustomerEntity {
    private String name;
    private AccountEntity account;
    private DebtEntity debt;

    public CustomerEntity(String name, AccountEntity account, DebtEntity debt) {
        this.name = name;
        this.account = account;
        this.debt = debt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public DebtEntity getDebt() {
        return debt;
    }

    public void setDebt(DebtEntity debt) {
        this.debt = debt;
    }
}
