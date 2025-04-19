package org.example;

import java.util.Map;
import java.util.Stack;

/**
 * Класс для вычисления математических выражений.
 * Поддерживает базовые арифметические операции, функции и переменные.
 */
public class MathExpression {

    /**
     * Проверяет, является ли символ математическим оператором.
     *
     * @param c проверяемый символ
     * @return true если символ является оператором (+, -, *, /, ^), иначе false
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /**
     * Определяет приоритет оператора для правильного вычисления выражений.
     *
     * @param operation символ оператора
     *@return числовой приоритет (1 для +/-, 2 для * и /, 3 для ^ )
    */

    private static int priorityOfOperator(char operation) {
        switch (operation) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
            default: return 0;
        }
    }

    /**
     * Выполняет математическую операцию над двумя числами.
     *
     * @param operation оператор (+, -, *, /, ^)
     * @param secondArg второй операнд
     * @param firstArg первый операнд
     * @return результат операции
     * @throws ArithmeticException при делении на ноль
     * @throws IllegalArgumentException при неизвестном операторе
     */
    private static double operationResult(char operation, double secondArg, double firstArg) {
        switch (operation) {
            case '+': return firstArg + secondArg;
            case '-': return firstArg - secondArg;
            case '*': return firstArg * secondArg;
            case '/':
                if (secondArg == 0) throw new ArithmeticException("Деление на ноль");
                return firstArg / secondArg;
            case '^': return Math.pow(firstArg, secondArg);
            default: throw new IllegalArgumentException("Неизвестный оператор");
        }
    }

    /**
     * Проверяет корректность математического выражения.
     *
     * @param input массив символов выражения
     * @return true если выражение синтаксически корректно, иначе false
     */
    private static boolean validate(char[] input) {
        Stack<Character> stack = new Stack<>();

        for (char symbol : input) {
            if (symbol == ' ') continue;
            if (symbol == '(') {
                stack.push(symbol);
            } else if (symbol == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            } else if (!Character.isDigit(symbol) && !isOperator(symbol) &&
                    symbol != '.' && !Character.isLetter(symbol)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    /**
     * Вычисляет значение математического выражения.
     *
     * @param expression строка с математическим выражением
     * @param variables карта значений переменных
     * @return результат вычисления
     * @throws IllegalArgumentException при некорректном выражении или неизвестной переменной
     * @throws ArithmeticException при математических ошибках (деление на ноль и т.д.)
     */
    public static double calculate(String expression, Map<String, Double> variables) {
        char[] tokens = expression.toCharArray();

        if (!validate(tokens)) {
            throw new IllegalArgumentException("Некорректное выражение");
        }
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') continue;

            if (Character.isDigit(tokens[i])) {
                StringBuilder numStr = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    numStr.append(tokens[i++]);
                }
                numbers.push(Double.parseDouble(numStr.toString()));
                i--;
            }
            else if (Character.isLetter(tokens[i])) {
                StringBuilder name = new StringBuilder();
                boolean isFunction = false;

                while (i < tokens.length && (Character.isLetter(tokens[i]) || tokens[i] == '(')) {
                    if (tokens[i] == '(') {
                        isFunction = true;
                        i++;
                        break;
                    }
                    name.append(tokens[i++]);
                }

                if (isFunction) {
                    String func = name.toString();
                    StringBuilder argStr = new StringBuilder();

                    while (i < tokens.length && tokens[i] != ')') {
                        argStr.append(tokens[i++]);
                    }

                    String argument = argStr.toString();
                    MathOperations function = MathOperations.getOperationByUserInput(func);

                    try {
                        double val = Double.parseDouble(argument);
                        numbers.push(function.calculate(val));
                    } catch (NumberFormatException e) {
                        if (!variables.containsKey(argument)) {
                            throw new IllegalArgumentException("Переменная не найдена");
                        }
                        numbers.push(function.calculate(variables.get(argument)));
                    }
                } else {
                    String var = name.toString();
                    if (!variables.containsKey(var)) {
                        throw new IllegalArgumentException("Переменная не найдена");
                    }
                    numbers.push(variables.get(var));
                    i--;
                }
            }
            else if (tokens[i] == '(') {
                operations.push(tokens[i]);
            }
            else if (tokens[i] == ')') {
                while (operations.peek() != '(') {
                    numbers.push(operationResult(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.pop();
            }
            else if (isOperator(tokens[i])) {
                while (!operations.empty() && priorityOfOperator(tokens[i]) <= priorityOfOperator(operations.peek())) {
                    numbers.push(operationResult(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.push(tokens[i]);
            }
        }

        while (!operations.empty()) {
            numbers.push(operationResult(operations.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    /**
     * Проверяет, является ли строка именем поддерживаемой функции.
     *
     * @param name проверяемая строка
     * @return true если имя соответствует поддерживаемой функции, иначе false
     */
    public static boolean isFunction(String name) {
        try {
            MathOperations.getOperationByUserInput(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}