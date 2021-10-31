package com.fuib.persistence.entities;

import java.math.BigDecimal;

@Deprecated
public class OperationEntity {
    private String id;
    private CustomerEntity from;
    private CustomerEntity to;
    private BigDecimal amount;

    public OperationEntity(String id, CustomerEntity from, CustomerEntity to, BigDecimal amount) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerEntity getFrom() {
        return from;
    }

    public void setFrom(CustomerEntity from) {
        this.from = from;
    }

    public CustomerEntity getTo() {
        return to;
    }

    public void setTo(CustomerEntity to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
