package com.fuib.strategies.impl;

import com.fuib.dto.Command;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.services.impl.CustomerService;
import com.fuib.strategies.CommandStrategy;

import java.math.BigDecimal;

public class DepositCommandStrategy implements CommandStrategy {

    private CustomerService customerService;

    public DepositCommandStrategy(Repository<CustomerEntity, String> customerRepository) {
        this.customerService = new CustomerService(customerRepository);
    }

    @Override
    public void execute(Command command) {
        var depositAmount = new BigDecimal(command.getArguments().get(0));
        customerService.deposit(command.getCustomerName(), depositAmount);
        var balance = customerService.getCustomer(command.getCustomerName()).getAccount().getBalance();

        System.out.println(
                String.format("Your balance is $%s",
                        balance.compareTo(BigDecimal.ZERO) == -1 ? "0" : balance.toString()));
    }
}
