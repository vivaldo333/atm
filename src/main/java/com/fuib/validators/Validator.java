package com.fuib.validators;

public interface Validator<T> {
    boolean isValid(T object);
}
