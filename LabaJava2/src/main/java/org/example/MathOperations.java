package org.example;

enum MathOperations {


    SIN("sin") {
        public double calculate(double x) { return Math.sin(Math.toRadians(x)); }
    },
    COS("cos") {
        public double calculate(double x) { return Math.cos(Math.toRadians(x)); }
    },
    TAN("tan") {
        public double calculate(double x) { return Math.tan(Math.toRadians(x)); }
    },
    SQRT("sqrt") {
        public double calculate(double x) {
            if (x < 0) throw new ArithmeticException("sqrt из отрицательного числа невозможен");
            return Math.sqrt(x);
        }
    },
    LOG("log") {
        public double calculate(double x) {
            if (x <= 0) throw new ArithmeticException("log не определен");
            return Math.log(x);
        }
    };


    public abstract double calculate(double x);

    private final String operation;
    public String getOperation() {
        return operation;
    }


    MathOperations(String operation) {
        this.operation = operation;
    }


    public static MathOperations getOperationByUserInput(String operation) {
        for (MathOperations mathOp : MathOperations.values()) {
            if (mathOp.operation.equals(operation)) {
                return mathOp;
            }
        }
        throw new IllegalArgumentException("Математическая операция не найдена");
    }

    public static boolean isFunction(String input) {
        try {
            getOperationByUserInput(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}