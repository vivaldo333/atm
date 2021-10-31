package com.fuib.strategies.impl;

import com.fuib.dto.Command;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.services.impl.CustomerService;
import com.fuib.strategies.CommandStrategy;

import java.math.BigDecimal;

public class WithdrawCommandStrategy implements CommandStrategy {

    private CustomerService customerService;

    public WithdrawCommandStrategy(Repository<CustomerEntity, String> customerRepository) {
        this.customerService = new CustomerService(customerRepository);
    }

    @Override
    public void execute(Command command) {
        var withdrawAmount = new BigDecimal(command.getArguments().get(0));
        customerService.withdraw(command.getCustomerName(), withdrawAmount);
        System.out.println(
                String.format("Your balance is $%s",
                        customerService.getCustomer(command.getCustomerName()).getAccount().getBalance().toString()));
    }
}
