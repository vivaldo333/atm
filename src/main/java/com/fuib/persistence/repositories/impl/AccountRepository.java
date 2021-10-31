package com.fuib.persistence.repositories.impl;

import com.fuib.persistence.entities.AccountEntity;
import com.fuib.persistence.repositories.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class AccountRepository implements Repository<AccountEntity, String> {

    private Map<String, AccountEntity> db = new ConcurrentHashMap<>();

    @Override
    public void save(AccountEntity entity) {
        db.put(entity.getId(), entity);
    }

    @Override
    public AccountEntity getById(String id) {
        return db.get(id);
    }

    @Override
    public boolean isExist(String id) {
        return db.containsKey(id);
    }
}
