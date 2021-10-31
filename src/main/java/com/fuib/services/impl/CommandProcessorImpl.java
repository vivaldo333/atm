package com.fuib.services.impl;

import com.fuib.convertors.Convertor;
import com.fuib.convertors.impl.CommandConvertor;
import com.fuib.dto.Command;
import com.fuib.enums.CommandType;
import com.fuib.factories.CommandProcessFactory;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.populators.Populator;
import com.fuib.populators.impl.CustomerNameToCommandPopulator;
import com.fuib.services.CommandProcessor;
import com.fuib.utils.Utils;
import com.fuib.validators.Validator;

import java.util.Scanner;

import static com.fuib.constants.Constant.Error.UNSUPPORTED_OPERATION_MSG;

public class CommandProcessorImpl implements CommandProcessor {

    private Scanner scanner;
    private Validator<String> commandValidator;
    private Repository<CustomerEntity, String> customerRepository;
    private Convertor<String, Command> commandConvertor;
    private Populator<String, Command> customerNameToCommandPopulator;
    private String lastLoggedCustomerName;

    public CommandProcessorImpl(
            Scanner scanner, Validator<String> commandValidator, Repository<CustomerEntity, String> customerRepository) {
        this.scanner = scanner;
        this.commandValidator = commandValidator;
        this.customerRepository = customerRepository;
        this.commandConvertor = new CommandConvertor();
        this.customerNameToCommandPopulator = new CustomerNameToCommandPopulator();
    }

    @Override
    public void runProcessing() {
        while (true) {
            var textCommand = scanner.nextLine();
            //validate
            if (commandValidator.isValid(textCommand)) {
                if (isExitCommand(textCommand)) {
                    break;
                }
                process(textCommand);
            } else {
                throw new IllegalArgumentException(UNSUPPORTED_OPERATION_MSG);
            }
        }
    }

    @Override
    public void process(String textCommand) {
        //extract
        var command = commandConvertor.convert(textCommand);
        //run
        var processStrategy = CommandProcessFactory.getCommandStrategy(customerRepository, command.getType());
        maintainCustomerName(command);
        processStrategy.execute(command);
    }

    private void maintainCustomerName(Command command) {
        var customerName = lastLoggedCustomerName;
        switch (command.getType()) {
            case LOGIN:
                customerName = command.getArguments().get(0);
                lastLoggedCustomerName = customerName;
                customerNameToCommandPopulator.populate(customerName, command);
                break;
            case LOGOUT:
                customerNameToCommandPopulator.populate(customerName, command);
                lastLoggedCustomerName = null;
                break;
            default:
                customerNameToCommandPopulator.populate(customerName, command);
                break;
        }
    }

    private boolean isExitCommand(String textCommand) {
        var firstCommandArgument = Utils.getCommandByIndex(textCommand, 0);
        return CommandType.EXIT.getName().equals(firstCommandArgument);
    }
}
