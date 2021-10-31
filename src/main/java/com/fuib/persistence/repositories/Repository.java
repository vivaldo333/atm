package com.fuib.persistence.repositories;

public interface Repository<T, ID> {
    void save(T entity);

    T getById(ID id);

    boolean isExist(ID id);
}
