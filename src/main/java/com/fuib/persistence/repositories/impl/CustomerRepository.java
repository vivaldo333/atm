package com.fuib.persistence.repositories.impl;

import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepository implements Repository<CustomerEntity, String> {

    private Map<String, CustomerEntity> db = new ConcurrentHashMap<>();

    @Override
    public void save(CustomerEntity entity) {
        db.put(entity.getName(), entity);
    }

    @Override
    public CustomerEntity getById(String id) {
        return db.get(id);
    }

    @Override
    public boolean isExist(String id) {
        return db.containsKey(id);
    }
}
