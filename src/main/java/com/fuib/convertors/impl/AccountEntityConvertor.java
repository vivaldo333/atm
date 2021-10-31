package com.fuib.convertors.impl;

import com.fuib.convertors.Convertor;
import com.fuib.persistence.entities.AccountEntity;

import java.math.BigDecimal;

public class AccountEntityConvertor implements Convertor<String, AccountEntity> {
    @Override
    public AccountEntity convert(String accountId) {
        return new AccountEntity(accountId, BigDecimal.ZERO);
    }
}
