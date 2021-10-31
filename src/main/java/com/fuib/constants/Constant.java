package com.fuib.constants;

public final class Constant {

    private Constant() {
        throw new UnsupportedOperationException(Error.UTILITY_CLASS_MSG);
    }

    public static class Common {
        private Common() {
            throw new UnsupportedOperationException(Error.UTILITY_CLASS_MSG);
        }

        public static final String SPACE = " ";
    }

    public static class Error {
        private Error() {
            throw new UnsupportedOperationException(Error.UTILITY_CLASS_MSG);
        }

        public static final String UTILITY_CLASS_MSG = "Utility class";
        public static final String UNREACHABLE_COMMAND_MSG = "Unreachable command argument";
        public static final String UNSUPPORTED_OPERATION_MSG = "Unsupported operation";
    }
}
