package com.fuib.strategies.impl;

import com.fuib.dto.Command;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.services.impl.CustomerService;
import com.fuib.strategies.CommandStrategy;

import java.util.Objects;

public class LoginCommandStrategy implements CommandStrategy {

    private CustomerService customerService;

    public LoginCommandStrategy(Repository<CustomerEntity, String> customerRepository) {
        this.customerService = new CustomerService(customerRepository);
    }

    @Override
    public void execute(Command command) {
        var customerName = command.getArguments().stream()
                .filter(Objects::nonNull)
                .filter(arg -> !arg.isBlank())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("customer name was not input"));
        customerService.login(customerName);
    }
}
