package com.fuib.strategies.impl;

import com.fuib.dto.Command;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.services.impl.CustomerService;
import com.fuib.strategies.CommandStrategy;

public class LogoutCommandStrategy implements CommandStrategy {

    private CustomerService customerService;

    public LogoutCommandStrategy(Repository<CustomerEntity, String> customerRepository) {
        this.customerService = new CustomerService(customerRepository);
    }

    @Override
    public void execute(Command command) {
        customerService.logout(command.getCustomerName());
    }
}
