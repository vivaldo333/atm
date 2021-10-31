package com.fuib.utils;

import com.fuib.constants.Constant;

import static com.fuib.constants.Constant.Error.UNREACHABLE_COMMAND_MSG;
import static com.fuib.constants.Constant.Error.UTILITY_CLASS_MSG;

public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException(UTILITY_CLASS_MSG);
    }

    public static String[] getAllCommands(String textCommands) {
        return textCommands.split(Constant.Common.SPACE);
    }

    public static String getCommandByIndex(String textCommands, int index) {
        var commands = getAllCommands(textCommands);
        if (isAvailableIndex(index, commands.length)) {
            return commands[index];
        }
        throw new IllegalArgumentException(UNREACHABLE_COMMAND_MSG);
    }

    private static boolean isAvailableIndex(int index, int arrayLength) {
        return arrayLength - 1 >= index;
    }

}
