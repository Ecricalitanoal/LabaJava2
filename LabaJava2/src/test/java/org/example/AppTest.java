package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class AppTest {

    private final Map<String, Double> testVariables = new HashMap<>();

    @Test
    void basicArithmeticOperations() {
        assertEquals(8.5, MathExpression.calculate("5 + 3.5", testVariables), 0.001);
        assertEquals(2.5, MathExpression.calculate("5 - 2.5", testVariables), 0.001);
        assertEquals(15, MathExpression.calculate("5 * 3", testVariables), 0.001);
        assertEquals(2.5, MathExpression.calculate("5 / 2", testVariables), 0.001);
    }

    @Test
    void exponentiationTest() {
        assertEquals(8, MathExpression.calculate("2 ^ 3", testVariables), 0.001);
        assertEquals(25, MathExpression.calculate("5 ^ 2", testVariables), 0.001);
    }

    @Test
    void nestedParenthesesCalculation() {
        assertEquals(20, MathExpression.calculate("(3 + 2) * 4", testVariables), 0.001);
        assertEquals(5, MathExpression.calculate("((2 + 3) * 3 + 5) / 4", testVariables), 0.001);
    }

    @Test
    void variableSubstitution() {
        testVariables.put("width", 5.0);
        testVariables.put("height", 3.0);
        assertEquals(15, MathExpression.calculate("width * height", testVariables), 0.001);
    }

    @Test
    void trigonometricFunctions() {
        assertEquals(1, MathExpression.calculate("sin(90)", testVariables), 0.001);
        assertEquals(0.5, MathExpression.calculate("cos(60)", testVariables), 0.001);
        assertEquals(1, MathExpression.calculate("tan(45)", testVariables), 0.001);
    }

    @Test
    void logarithmicAndRootFunctions() {
        assertEquals(2, MathExpression.calculate("sqrt(4)", testVariables), 0.001);
        assertEquals(2.302585, MathExpression.calculate("log(10)", testVariables), 0.000001);
    }

    @Test
    void combinedOperations() {
        assertEquals(5, MathExpression.calculate("sin(90) * 5 + cos(90) * 2", testVariables), 0.001);
        assertEquals(3.1512, MathExpression.calculate("(sqrt(16) + log(10)) / 2", testVariables), 0.001);
    }

    @Test
    void invalidExpressionHandling() {
        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("2 + * +) 3", testVariables));

        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("(2 + 3", testVariables));
    }

    @Test
    void undefinedVariableHandling() {
        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("x + 2", testVariables));
    }

    @Test
    void divisionByZeroProtection() {
        assertThrows(ArithmeticException.class,
                () -> MathExpression.calculate("5 / 0", testVariables));
    }

    @Test
    void invalidFunctionHandling() {
        assertThrows(IllegalArgumentException.class,
                () -> MathExpression.calculate("tang(45)", testVariables));
    }

    @Test
    void decimalNumberProcessing() {
        assertEquals(0.75, MathExpression.calculate("0.5 + 0.25", testVariables), 0.001);
        assertEquals(1.5, MathExpression.calculate("0.5 * 3", testVariables), 0.001);
    }

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
}