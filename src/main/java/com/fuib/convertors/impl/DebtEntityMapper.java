package com.fuib.convertors.impl;

import com.fuib.persistence.entities.DebtEntity;

import java.util.concurrent.ConcurrentHashMap;

public class DebtEntityMapper {
    public DebtEntity create() {
        return new DebtEntity(new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }
}
