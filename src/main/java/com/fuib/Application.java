package com.fuib;

import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.persistence.repositories.impl.CustomerRepository;
import com.fuib.services.CommandProcessor;
import com.fuib.services.impl.CommandProcessorImpl;
import com.fuib.validators.Validator;
import com.fuib.validators.impl.CommandValidator;

import java.util.Scanner;

public class Application {
    public static void main(final String[] arguments) {
        Repository<CustomerEntity, String> customerRepository = new CustomerRepository();
        Scanner consoleScanner = new Scanner(System.in);
        Validator<String> commandValidator = new CommandValidator();
        CommandProcessor commandProcessor = new CommandProcessorImpl(consoleScanner, commandValidator, customerRepository);
        commandProcessor.runProcessing();
    }

    public static void test(final String[] arguments) {
        Repository<CustomerEntity, String> customerRepository = new CustomerRepository();
        Scanner consoleScanner = new Scanner(System.in);
        Validator<String> commandValidator = new CommandValidator();
        CommandProcessor commandProcessor = new CommandProcessorImpl(consoleScanner, commandValidator, customerRepository);
        System.out.println("--login Alice--PASSED--------------------------");
        commandProcessor.process("login Alice");
        System.out.println("--deposit 100--PASSED--------------------------");
        commandProcessor.process("deposit 100");
        System.out.println("--logout--PASSED--------------------------");
        commandProcessor.process("logout");
        System.out.println("--login Bob--PASSED--------------------------");
        commandProcessor.process("login Bob");
        System.out.println("--deposit 80--PASSED--------------------------");
        commandProcessor.process("deposit 80");
        System.out.println("--transfer Alice 50--PASSED--------------------------");
        commandProcessor.process("transfer Alice 50");
        System.out.println("--transfer Alice 100--PASSED--------------------------");
        commandProcessor.process("transfer Alice 100");
        System.out.println("--deposit 30--PASSED--------------------------");
        commandProcessor.process("deposit 30");
        System.out.println("--logout--PASSED--------------------------");
        commandProcessor.process("logout");
        System.out.println("--login Alice--NOT PASSED (I am not agree with logic)--------------------------");
        commandProcessor.process("login Alice");
        System.out.println("--transfer Bob 30--NOT PASSED--------------------------");
        commandProcessor.process("transfer Bob 30");
        System.out.println("--logout--PASSED--------------------------");
        commandProcessor.process("logout");
        System.out.println("--login Bob--NOT PASSED--------------------------");
        commandProcessor.process("login Bob");
        System.out.println("--deposit 100--NOT PASSED--------------------------");
        commandProcessor.process("deposit 100");
        System.out.println("--logout--PASSED--------------------------");
        commandProcessor.process("logout");
    }

}
