package com.fuib.strategies;

import com.fuib.dto.Command;

public interface CommandStrategy {
    void execute(Command command);
}
