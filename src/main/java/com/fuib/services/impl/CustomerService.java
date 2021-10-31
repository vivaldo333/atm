package com.fuib.services.impl;

import com.fuib.convertors.Convertor;
import com.fuib.convertors.impl.CustomerEntityConvertor;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerService {
    private Repository<CustomerEntity, String> customerRepository;
    private DebtService debtService;
    private Convertor<String, CustomerEntity> customerEntityConvertor;

    public CustomerService(Repository<CustomerEntity, String> customerRepository) {
        this.customerRepository = customerRepository;
        this.debtService = new DebtService();
        this.customerEntityConvertor = new CustomerEntityConvertor();
    }

    public CustomerEntity getCustomer(String customerName) {
        return customerRepository.getById(customerName);
    }

    public void login(String customerName) {
        System.out.println(
                String.format("Hello, %s!", customerName));
        if (customerRepository.isExist(customerName)) {
            var customer = customerRepository.getById(customerName);
            var balance = customer.getAccount().getBalance();
            var isBalanceLessZero = balance.longValue() < 0L;

            var debts = customer.getDebt();
            var debtAmount = new AtomicLong(0);
            var lendAmount = new AtomicLong(0);
            var debtMessage = new StringBuffer();
            var lendMessage = new StringBuffer();
            if (!debts.getOwnedFromCustomerAndAmount().isEmpty()) {
                debts.getOwnedFromCustomerAndAmount().entrySet().stream()
                        .filter(Objects::nonNull)
                        .filter(entry -> !entry.getValue().equals(BigDecimal.ZERO))
                        .forEach(entry -> {
                            var debt = entry.getValue();
                            debtAmount.addAndGet(debt.longValue());
                            debtMessage.append(String.format("Owed $%s from %s", debt.toString(), entry.getKey()));
                        });
            }
            if (!debts.getOwnedToCustomerAndAmount().isEmpty()) {
                debts.getOwnedFromCustomerAndAmount().entrySet().stream()
                        .filter(Objects::nonNull)
                        .filter(entry -> !entry.getValue().equals(BigDecimal.ZERO))
                        .forEach(entry -> {
                            var lend = entry.getValue();
                            lendAmount.addAndGet(lend.longValue());
                            lendMessage.append(String.format("Owed $%s to %s", lend.toString(), entry.getKey()));
                        });
            }

            var correctedBalance = isBalanceLessZero ? "0" : balance.toString();
            System.out.println(
                    String.format("Your balance is $%s",
                            correctedBalance));
            if (debtAmount.get() != 0) {
                System.out.println(debtMessage);
            }
            if (lendAmount.get() != 0) {
                System.out.println(lendAmount);
            }

        } else {
            var customer = customerEntityConvertor.convert(customerName);
            customerRepository.save(customer);
            System.out.println("Your balance is $0");
        }
    }

    public void deposit(String customerName, BigDecimal depositAmount) {
        deposit(customerName, depositAmount, Boolean.TRUE);
    }

    public void deposit(String customerName, BigDecimal depositAmount, boolean isNeedMaintainDepts) {
        if (customerRepository.isExist(customerName)) {
            //transfer
            var customer = customerRepository.getById(customerName);
            var account = customer.getAccount();
            synchronized (this) {
                synchronized (account) {
                    if (isGreatThanZero(account.getDebtBalance())) {
                        var passedDebtAmount = account.getDebtBalance().compareTo(depositAmount) == 1
                                ? depositAmount
                                : depositAmount.subtract(account.getDebtBalance());
                        account.setDebtBalance(account.getDebtBalance().subtract(passedDebtAmount));
                        account.setBalance(account.getBalance().add(depositAmount.subtract(passedDebtAmount)));
                        if (isNeedMaintainDepts) {
                            maintainDebts(passedDebtAmount, customer);
                        }
                    } else {
                        account.setBalance(account.getBalance().add(depositAmount));
                    }
                }
            }
        } else {
            throw new RuntimeException("Customer " + customerName + " was not found");
        }
    }

    private boolean isGreatThanZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) == 1;
    }

    private void maintainDebts(BigDecimal passedDebtAmount, CustomerEntity customer) {
        var customerDebts = customer.getDebt().getOwnedToCustomerAndAmount();
        var totalDepositAmount = BigDecimal.valueOf(passedDebtAmount.longValue());
        if (!customerDebts.isEmpty()) {
            for (Map.Entry<String, BigDecimal> entry : customerDebts.entrySet()) {
                var toCustomerName = entry.getKey();
                var toCustomer = customerRepository.getById(toCustomerName);
                var debtAmount = entry.getValue();
                if (!debtAmount.equals(BigDecimal.ZERO)
                        && !isLess(debtAmount, BigDecimal.ZERO)
                        && !totalDepositAmount.equals(BigDecimal.ZERO)
                        && !isLess(totalDepositAmount, BigDecimal.ZERO)) {
                    totalDepositAmount = totalDepositAmount.subtract(passedDebtAmount);
                    deposit(toCustomerName, passedDebtAmount, Boolean.FALSE);
                    processDebts(customer, toCustomer, passedDebtAmount);
                } else {
                    break;
                }
            }
        }
    }

    /*private BigDecimal getMaintainingDebts(BigDecimal depositAmount, CustomerEntity customer) {
        var customerDebts = customer.getDebt().getOwnedToCustomerAndAmount();
        var totalDepositAmount = BigDecimal.valueOf(depositAmount.longValue());
        if (!customerDebts.isEmpty()) {
            for (Map.Entry<String, BigDecimal> entry : customerDebts.entrySet()) {
                var toCustomer = entry.getKey();
                var debtAmount = entry.getValue();
                if (!debtAmount.equals(BigDecimal.ZERO)
                        && isLess(debtAmount, BigDecimal.ZERO)
                        && !totalDepositAmount.equals(BigDecimal.ZERO)
                        && !isLess(totalDepositAmount, BigDecimal.ZERO)) {
                    var transferAmount = isLess(totalDepositAmount, debtAmount.abs())
                            ? totalDepositAmount
                            : depositAmount.abs().subtract(debtAmount);
                    totalDepositAmount = totalDepositAmount.subtract(transferAmount);
                    transfer(customer.getName(), toCustomer, transferAmount);
                } else {
                    break;
                }
            }
            return totalDepositAmount;
        }
        return depositAmount;
    }*/

    public void withdraw(String customerName, BigDecimal withdrawAmount) {
        if (customerRepository.isExist(customerName)) {
            var customer = customerRepository.getById(customerName);
            synchronized (this) {
                var account = customer.getAccount();
                synchronized (account) {
                    /*var balance = account.getBalance();
                    if (isLess(balance, withdrawAmount)) {
                        throw new UnsupportedOperationException("Not enough money on account for withdraw");
                    }*/
                    var diffAmount = withdrawAmount.subtract(account.getBalance());
                    if (isGreatThanZero(diffAmount)) {
                        account.setDebtBalance(account.getDebtBalance().add(diffAmount));
                        account.setBalance(BigDecimal.ZERO);
                    } else {
                        account.setBalance(account.getBalance().subtract(withdrawAmount));
                    }
                }
            }
        } else {
            throw new RuntimeException("Customer " + customerName + " was not found");
        }
    }

    public void transfer(String fromCustomerName, String toCustomerName, BigDecimal transferAmount) {
        if (customerRepository.isExist(fromCustomerName) && customerRepository.isExist(toCustomerName)) {
            var fromCustomer = customerRepository.getById(fromCustomerName);
            var toCustomer = customerRepository.getById(toCustomerName);
            synchronized (this) {
                var fromAccount = fromCustomer.getAccount();
                synchronized (fromAccount) {
                    var toAccount = toCustomer.getAccount();
                    synchronized (toAccount) {
                        withdraw(fromCustomerName, transferAmount);
                        deposit(toCustomerName, transferAmount, Boolean.FALSE);
                        processDebts(fromCustomer, toCustomer, transferAmount);
                    }
                }
            }
        } else {
            throw new RuntimeException("Customers were not found during transfer money");
        }
    }

    private void processDebts(CustomerEntity fromCustomer, CustomerEntity toCustomer, BigDecimal transferAmount) {
        var fromAccount = fromCustomer.getAccount();
        var beforeChangeDebtSum = fromCustomer.getDebt().getOwnedToCustomerAndAmount().values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var diffDebts = fromAccount.getDebtBalance().subtract(beforeChangeDebtSum);
        if (isGreatThanZero(diffDebts) || isGreatThanZero(fromAccount.getDebtBalance())) {
            debtService.setOwnedTo(fromCustomer, toCustomer.getName(), diffDebts);
            debtService.setOwnedFrom(toCustomer, fromCustomer.getName(), diffDebts);
            System.out.println(
                    String.format("Transferred $%s to %s",
                            isGreatThanZero(diffDebts) ? transferAmount.subtract(diffDebts) : diffDebts.negate(),
                            toCustomer.getName()));
            System.out.println("Your balance is $0");
            var toCustomerDebtSum = fromCustomer.getDebt().getOwnedToCustomerAndAmount().entrySet().stream()
                    .filter(entry -> entry.getKey().equals(toCustomer.getName()))
                    .map(Map.Entry::getValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println(
                    String.format("Owed $%s to %s",
                            toCustomerDebtSum.toString(),
                            toCustomer.getName()));
        } else {
            System.out.println(
                    String.format("Transferred $%s to %s", transferAmount.toString(), toCustomer.getName()));
            System.out.println(
                    String.format("Your balance is $%s", fromAccount.getBalance().toString()));
        }
    }

    /*private BigDecimal getProcessedDebts(CustomerEntity fromCustomer, CustomerEntity toCustomer, AccountEntity fromAccount, BigDecimal transferAmount) {
        var originBalance = fromAccount.getBalance().add(transferAmount);
        //if (isLess(originBalance, transferAmount)) {
        if (isLess(fromAccount.getBalance(), BigDecimal.ZERO)) {
            var isBalanceNegative = isLess(originBalance, BigDecimal.ZERO);
            var debtAmount = isBalanceNegative
                    ? transferAmount.negate()
                    : transferAmount.subtract(originBalance).negate();
            //var correctedDebtAmount = isBalanceNegative ? originBalance.abs().add(debtAmount) : debtAmount.negate();
            debtService.setOwnedTo(fromCustomer, toCustomer.getName(), debtAmount);
            debtService.setOwnedFrom(toCustomer, fromCustomer.getName(), debtAmount);

            System.out.println(
                    String.format("Transferred $%s to %s",
                            transferAmount.add(debtAmount),
                            toCustomer.getName()));
            System.out.println("Your balance is $0");
            System.out.println(
                    String.format("Owed $%s to %s",
                            debtAmount.abs().toString(),
                            toCustomer.getName()));
            return debtAmount.negate();
        } else {
            System.out.println(
                    String.format("Transferred $%s to %s", transferAmount.toString(), toCustomer.getName()));
            System.out.println(
                    String.format("Your balance is $%s", fromAccount.getBalance().toString()));
            return transferAmount;
        }
    }*/

    public void logout(String customerName) {
        System.out.println(
                String.format("Goodbye, %s!", customerName));
    }


    private boolean isLess(BigDecimal balance, BigDecimal withdrawAmount) {
        return balance.compareTo(withdrawAmount) == -1;
    }
}
