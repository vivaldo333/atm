package com.fuib.convertors.impl;

import com.fuib.convertors.Convertor;
import com.fuib.dto.Command;
import com.fuib.enums.CommandType;
import com.fuib.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandConvertor implements Convertor<String, Command> {
    @Override
    public Command convert(String source) {
        var commands = Utils.getAllCommands(source);
        return new Command(getCommandType(commands), getCommandArguments(commands));
    }

    private List<String> getCommandArguments(String[] commands) {
        return Arrays.stream(commands)
                .skip(1)
                .collect(Collectors.toList());
    }

    private CommandType getCommandType(String[] commands) {
        return CommandType.getCommandTypes().get(commands[0]);
    }
}
