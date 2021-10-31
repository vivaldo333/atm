package com.fuib.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CommandType {
    LOGIN(CommandTypeValues.LOGIN_COMMAND),
    DEPOSIT(CommandTypeValues.DEPOSIT_COMMAND),
    WITHDRAW(CommandTypeValues.WITHDRAW_COMMAND),
    TRANSFER(CommandTypeValues.TRANSFER_COMMAND),
    LOGOUT(CommandTypeValues.LOGOUT_COMMAND),
    EXIT(CommandTypeValues.EXIT_COMMAND);

    private final String name;

    private static final Map<String, CommandType> COMMAND_TYPE = initCommandTypes();

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Map<String, CommandType> getCommandTypes() {
        return COMMAND_TYPE;
    }

    private static class CommandTypeValues {
        private CommandTypeValues() {
            throw new UnsupportedOperationException("Utility class");
        }

        public static final String LOGIN_COMMAND = "login";
        public static final String DEPOSIT_COMMAND = "deposit";
        public static final String WITHDRAW_COMMAND = "withdraw";
        public static final String TRANSFER_COMMAND = "transfer";
        public static final String LOGOUT_COMMAND = "logout";
        public static final String EXIT_COMMAND = "exit";
    }

    private static Map<String, CommandType> initCommandTypes() {
        return Arrays.stream(CommandType.values())
                .collect(Collectors.toMap(
                        CommandType::getName,
                        Function.identity()
                ));
    }
}
