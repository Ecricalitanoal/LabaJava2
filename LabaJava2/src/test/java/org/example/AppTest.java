package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для тестирования функциональности математического калькулятора.
 * Содержит тесты для проверки корректности вычислений, обработки ошибок
 * и работы с переменными и функциями.
 */
class AppTest {

    private final Map<String, Double> testVariables = new HashMap<>();

    /**
     * Тестирование базовых арифметических операций:
     * сложения, вычитания, умножения и деления.
     */
    @Test
    void basicArithmeticOperations() {
        assertEquals(8.5, MathExpression.calculate("5 + 3.5", testVariables), 0.001);
        assertEquals(2.5, MathExpression.calculate("5 - 2.5", testVariables), 0.001);
        assertEquals(15, MathExpression.calculate("5 * 3", testVariables), 0.001);
        assertEquals(2.5, MathExpression.calculate("5 / 2", testVariables), 0.001);
    }

    /**
     * Тестирование операции возведения в степень.
     * Проверяет корректность вычисления для разных оснований и степеней.
     */
    @Test
    void exponentiationTest() {
        assertEquals(8, MathExpression.calculate("2 ^ 3", testVariables), 0.001);
        assertEquals(25, MathExpression.calculate("5 ^ 2", testVariables), 0.001);
        assertEquals(1, MathExpression.calculate("5 ^ 0", testVariables), 0.001);
    }

    /**
     * Тестирование вложенных скобок в выражениях.
     * Проверяет корректность обработки сложных скобочных конструкций.
     */
    @Test
    void nestedParenthesesCalculation() {
        assertEquals(20, MathExpression.calculate("(3 + 2) * 4", testVariables), 0.001);
        assertEquals(5, MathExpression.calculate("((2 + 3) * 3 + 5) / 4", testVariables), 0.001);
        assertEquals(14, MathExpression.calculate("2 * (3 + 4)", testVariables), 0.001);
    }

    /**
     * Тестирование подстановки переменных в выражения.
     * Проверяет корректность замены переменных на их значения.
     */
    @Test
    void variableSubstitution() {
        testVariables.put("width", 5.0);
        testVariables.put("height", 3.0);
        assertEquals(15, MathExpression.calculate("width * height", testVariables), 0.001);
        assertEquals(8, MathExpression.calculate("width + height", testVariables), 0.001);
    }

    /**
     * Тестирование тригонометрических функций:
     * синуса, косинуса и тангенса.
     * Проверяет корректность вычислений для стандартных углов.
     */
    @Test
    void trigonometricFunctions() {
        assertEquals(1, MathExpression.calculate("sin(90)", testVariables), 0.001);
        assertEquals(0.5, MathExpression.calculate("cos(60)", testVariables), 0.001);
        assertEquals(1, MathExpression.calculate("tan(45)", testVariables), 0.001);
    }

    /**
     * Тестирование логарифмической функции и квадратного корня.
     * Проверяет корректность вычислений для стандартных значений.
     */
    @Test
    void logarithmicAndRootFunctions() {
        assertEquals(2, MathExpression.calculate("sqrt(4)", testVariables), 0.001);
        assertEquals(2.302585, MathExpression.calculate("log(10)", testVariables), 0.000001);
        assertEquals(3, MathExpression.calculate("sqrt(9)", testVariables), 0.001);
    }

    /**
     * Тестирование комбинированных выражений с использованием
     * различных операций и функций.
     */
    @Test
    void combinedOperations() {
        assertEquals(5, MathExpression.calculate("sin(90) * 5 + cos(90) * 2", testVariables), 0.001);
        assertEquals(3.1512, MathExpression.calculate("(sqrt(16) + log(10)) / 2", testVariables), 0.001);
        assertEquals(6, MathExpression.calculate("2 + 2 * 2", testVariables), 0.001);
    }

    /**
     * Тестирование обработки некорректных выражений.
     * Проверяет генерацию исключений при синтаксических ошибках.
     */
    @Test
    void invalidExpressionHandling() {
        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("2 + * +) 3", testVariables));

        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("(2 + 3", testVariables));

        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("2 + 3)", testVariables));
    }

    /**
     * Тестирование обработки неизвестных переменных.
     * Проверяет генерацию исключений при использовании неопределенных переменных.
     */
    @Test
    void undefinedVariableHandling() {
        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("x + 2", testVariables));

        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("a * b + c", testVariables));
    }

    /**
     * Тестирование защиты от деления на ноль.
     * Проверяет генерацию исключения при попытке деления на ноль.
     */
    @Test
    void divisionByZeroProtection() {
        assertThrows(ArithmeticException.class,
                () -> MathExpression.calculate("5 / 0", testVariables));

        assertThrows(ArithmeticException.class,
                () -> MathExpression.calculate("1 / (2 - 2)", testVariables));
    }

    /**
     * Тестирование обработки неизвестных функций.
     * Проверяет генерацию исключений при использовании несуществующих функций.
     */
    @Test
    void invalidFunctionHandling() {
        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("tang(45)", testVariables));

        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("log10(100)", testVariables));
    }

    /**
     * Тестирование обработки десятичных чисел.
     * Проверяет корректность вычислений с дробными числами.
     */
    @Test
    void decimalNumberProcessing() {
        assertEquals(0.75, MathExpression.calculate("0.5 + 0.25", testVariables), 0.001);
        assertEquals(1.5, MathExpression.calculate("0.5 * 3", testVariables), 0.001);
        assertEquals(0.333, MathExpression.calculate("1 / 3", testVariables), 0.001);
    }

    /**
     * Тестирование сложных выражений, включающих все возможности калькулятора:
     * переменные, функции, различные операции и скобки.
     */
    @Test
    void complexExpressionWithAllFeatures() {
        testVariables.put("base", 2.0);
        testVariables.put("angle", 30.0);
        double result = MathExpression.calculate(
                "(sin(angle) * 10 + cos(angle) * 5) / (base ^ 2) + sqrt(9)",
                testVariables
        );
        assertEquals(5.3325, result, 0.001);
    }

    /**
     * Тестирование приоритета операций.
     * Проверяет правильность соблюдения приоритетов операций.
     */
    @Test
    void operatorPrecedenceTest() {
        assertEquals(14, MathExpression.calculate("2 + 3 * 4", testVariables), 0.001);
        assertEquals(20, MathExpression.calculate("(2 + 3) * 4", testVariables), 0.001);
        assertEquals(10, MathExpression.calculate("2 * 3 + 4", testVariables), 0.001);
    }

    /**
     * Тестирование унарного минуса.
     * Проверяет корректность обработки отрицательных чисел.
     */
    @Test
    void unaryMinusTest() {
        assertEquals(-5, MathExpression.calculate("-5", testVariables), 0.001);
        assertEquals(3, MathExpression.calculate("5 + -2", testVariables), 0.001);
        assertEquals(-15, MathExpression.calculate("-3 * 5", testVariables), 0.001);
    }

    /**
     * Тестирование обработки пробелов в выражениях.
     * Проверяет корректность вычислений при наличии пробелов.
     */
    @Test
    void whitespaceHandlingTest() {
        assertEquals(5, MathExpression.calculate("2 + 3", testVariables), 0.001);
        assertEquals(5, MathExpression.calculate("2+3", testVariables), 0.001);
        assertEquals(5, MathExpression.calculate(" 2 + 3 ", testVariables), 0.001);
    }
}