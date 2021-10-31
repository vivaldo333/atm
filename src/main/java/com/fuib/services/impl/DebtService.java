package com.fuib.services.impl;

import com.fuib.persistence.entities.CustomerEntity;

import java.math.BigDecimal;
import java.util.Map;

public class DebtService {

    public void setOwnedTo(CustomerEntity fromCustomer, String toCustomerName, BigDecimal debtAmount) {
        var ownerToDebts = fromCustomer.getDebt().getOwnedToCustomerAndAmount();
        updateCustomerDebts(toCustomerName, debtAmount, ownerToDebts);
    }

    public void setOwnedFrom(CustomerEntity toCustomer, String fromCustomerName, BigDecimal debtAmount) {
        var ownerFromDebts = toCustomer.getDebt().getOwnedFromCustomerAndAmount();
        updateCustomerDebts(fromCustomerName, debtAmount, ownerFromDebts);
    }

    private void updateCustomerDebts(String customerName, BigDecimal debtAmount, Map<String, BigDecimal> debts) {
        var totalDebtAmount = BigDecimal.ZERO;
        if (debts.containsKey(customerName)) {
            totalDebtAmount = debts.get(customerName);
        }
        debts.put(customerName, totalDebtAmount.add(debtAmount));
    }
}
