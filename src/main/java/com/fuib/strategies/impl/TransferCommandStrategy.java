package com.fuib.strategies.impl;

import com.fuib.dto.Command;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.services.impl.CustomerService;
import com.fuib.strategies.CommandStrategy;

import java.math.BigDecimal;

public class TransferCommandStrategy implements CommandStrategy {

    private CustomerService customerService;

    public TransferCommandStrategy(Repository<CustomerEntity, String> customerRepository) {
        this.customerService = new CustomerService(customerRepository);
    }

    @Override
    public void execute(Command command) {
        var fromCustomerName = command.getCustomerName();
        var toCustomerName = command.getArguments().get(0);
        var transferAmount = new BigDecimal(command.getArguments().get(1));
        customerService.transfer(fromCustomerName, toCustomerName, transferAmount);
    }
}
