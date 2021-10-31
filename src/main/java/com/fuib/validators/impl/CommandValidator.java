package com.fuib.validators.impl;

import com.fuib.enums.CommandType;
import com.fuib.utils.Utils;
import com.fuib.validators.Validator;

import static java.util.Objects.nonNull;

public class CommandValidator implements Validator<String> {
    @Override
    public boolean isValid(String textCommand) {
        return nonNull(textCommand)
                && !textCommand.isBlank()
                && isCommandPresent(textCommand);
    }

    private boolean isCommandPresent(String textCommand) {
        var allCommandArguments = Utils.getAllCommands(textCommand);
        if (allCommandArguments.length > 0) {
            var firstCommandArgument = Utils.getCommandByIndex(textCommand, 0);
            return CommandType.getCommandTypes().containsKey(firstCommandArgument);
        }
        return false;
    }
}
