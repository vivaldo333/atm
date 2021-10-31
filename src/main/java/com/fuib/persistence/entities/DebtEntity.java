package com.fuib.persistence.entities;

import java.math.BigDecimal;
import java.util.Map;

public class DebtEntity {
    private Map<String, BigDecimal> ownedToCustomerAndAmount; //-
    private Map<String, BigDecimal> ownedFromCustomerAndAmount; //+

    public DebtEntity(Map<String, BigDecimal> ownedToCustomerAndAmount, Map<String, BigDecimal> ownedFromCustomerAndAmount) {
        this.ownedToCustomerAndAmount = ownedToCustomerAndAmount;
        this.ownedFromCustomerAndAmount = ownedFromCustomerAndAmount;
    }

    public Map<String, BigDecimal> getOwnedToCustomerAndAmount() {
        return ownedToCustomerAndAmount;
    }

    public void setOwnedToCustomerAndAmount(Map<String, BigDecimal> ownedToCustomerAndAmount) {
        this.ownedToCustomerAndAmount = ownedToCustomerAndAmount;
    }

    public Map<String, BigDecimal> getOwnedFromCustomerAndAmount() {
        return ownedFromCustomerAndAmount;
    }

    public void setOwnedFromCustomerAndAmount(Map<String, BigDecimal> ownedFromCustomerAndAmount) {
        this.ownedFromCustomerAndAmount = ownedFromCustomerAndAmount;
    }
}
