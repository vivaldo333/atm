package com.fuib.populators.impl;

import com.fuib.dto.Command;
import com.fuib.populators.Populator;

public class CustomerNameToCommandPopulator implements Populator<String, Command> {
    @Override
    public void populate(String source, Command target) {
        target.setCustomerName(source);
    }
}
