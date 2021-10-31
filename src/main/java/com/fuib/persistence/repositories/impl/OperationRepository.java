package com.fuib.persistence.repositories.impl;

import com.fuib.persistence.entities.OperationEntity;
import com.fuib.persistence.repositories.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Deprecated
public class OperationRepository implements Repository<OperationEntity, String> {

    private Map<String, OperationEntity> db = new ConcurrentHashMap<>();

    @Override
    public void save(OperationEntity entity) {
        db.put(entity.getId(), entity);
    }

    @Override
    public OperationEntity getById(String id) {
        return db.get(id);
    }

    @Override
    public boolean isExist(String id) {
        return db.containsKey(id);
    }

    public List<OperationEntity> getOperationByTo(String toCustomerName) {
        return db.values().stream()
                .filter(operation -> operation.getTo().getName().equals(toCustomerName))
                .collect(Collectors.toList());
    }

    public List<OperationEntity> getOperationByFrom(String toCustomerName) {
        return db.values().stream()
                .filter(operation -> operation.getFrom().getName().equals(toCustomerName))
                .collect(Collectors.toList());
    }
}
