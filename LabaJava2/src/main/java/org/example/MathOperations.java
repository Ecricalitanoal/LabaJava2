package org.example;

/**
 * Перечисление поддерживаемых математических операций.
 * Каждая операция имеет имя и метод вычисления.
 */
enum MathOperations {

    /** Синус угла в градусах */
    SIN("sin") {
        public double calculate(double x) { return Math.sin(Math.toRadians(x)); }
    },

    /** Косинус угла в градусах */
    COS("cos") {
        public double calculate(double x) { return Math.cos(Math.toRadians(x)); }
    },

    /** Тангенс угла в градусах */
    TAN("tan") {
        public double calculate(double x) { return Math.tan(Math.toRadians(x)); }
    },

    /** Квадратный корень числа */
    SQRT("sqrt") {
        public double calculate(double x) {
            if (x < 0) throw new ArithmeticException("sqrt из отрицательного числа невозможен");
            return Math.sqrt(x);
        }
    },

    /** Натуральный логарифм числа */
    LOG("log") {
        public double calculate(double x) {
            if (x <= 0) throw new ArithmeticException("log не определен");
            return Math.log(x);
        }
    };

    /**
     * Вычисляет результат математической операции.
     * @param x входное значение
     * @return результат вычисления
     * @throws ArithmeticException при недопустимых входных значениях
     */
    public abstract double calculate(double x);

    private final String operation;

    /**
     * Возвращает строковое представление операции.
     * @return имя операции
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Конструктор операции.
     * @param operation имя операции
     */
    MathOperations(String operation) {
        this.operation = operation;
    }

    /**
     * Возвращает операцию по её имени.
     * @param operation имя операции
     * @return объект операции
     * @throws IllegalArgumentException если операция не найдена
     */
    public static MathOperations getOperationByUserInput(String operation) {
        for (MathOperations mathOp : MathOperations.values()) {
            if (mathOp.operation.equals(operation)) {
                return mathOp;
            }
        }
        throw new IllegalArgumentException("Математическая операция не найдена");
    }
}