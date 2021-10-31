package com.fuib.dto;

import com.fuib.enums.CommandType;

import java.util.List;

public class Command {
    private CommandType type;
    private List<String> arguments;
    private String customerName;

    public Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public CommandType getType() {
        return type;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
