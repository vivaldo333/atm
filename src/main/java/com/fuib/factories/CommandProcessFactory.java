package com.fuib.factories;

import com.fuib.enums.CommandType;
import com.fuib.persistence.entities.CustomerEntity;
import com.fuib.persistence.repositories.Repository;
import com.fuib.strategies.CommandStrategy;
import com.fuib.strategies.impl.DepositCommandStrategy;
import com.fuib.strategies.impl.LoginCommandStrategy;
import com.fuib.strategies.impl.LogoutCommandStrategy;
import com.fuib.strategies.impl.TransferCommandStrategy;
import com.fuib.strategies.impl.WithdrawCommandStrategy;

import static com.fuib.constants.Constant.Error.UNSUPPORTED_OPERATION_MSG;

public final class CommandProcessFactory {
    private CommandProcessFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CommandStrategy getCommandStrategy(Repository<CustomerEntity, String> customerRepository, CommandType commandType) {
        switch (commandType) {
            case LOGIN:
                return new LoginCommandStrategy(customerRepository);
            case DEPOSIT:
                return new DepositCommandStrategy(customerRepository);
            case WITHDRAW:
                return new WithdrawCommandStrategy(customerRepository);
            case TRANSFER:
                return new TransferCommandStrategy(customerRepository);
            case LOGOUT:
                return new LogoutCommandStrategy(customerRepository);
            default:
        }
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MSG);
    }
}
