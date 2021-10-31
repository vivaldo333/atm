package com.fuib.convertors.impl;

import com.fuib.convertors.Convertor;
import com.fuib.persistence.entities.AccountEntity;
import com.fuib.persistence.entities.CustomerEntity;

import java.util.UUID;

public class CustomerEntityConvertor implements Convertor<String, CustomerEntity> {

    private Convertor<String, AccountEntity> accountEntityConvertor;
    private DebtEntityMapper debtEntityMapper;

    public CustomerEntityConvertor() {
        this.accountEntityConvertor = new AccountEntityConvertor();
        this.debtEntityMapper = new DebtEntityMapper();
    }


    @Override
    public CustomerEntity convert(String customerName) {
        var account = accountEntityConvertor.convert(UUID.randomUUID().toString());
        var debts = debtEntityMapper.create();
        return new CustomerEntity(customerName, account, debts);
    }
}
