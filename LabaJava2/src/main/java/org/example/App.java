package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Главный класс приложения - консольный математический калькулятор.
 * Позволяет вычислять выражения с переменными и функциями.
 */
public class App {

    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.println("Математический калькулятор");
        System.out.println("Доступные функции: sin, cos, tan, sqrt, log");
        System.out.println("Пример: (sin(90)*12+12)*2-8*8^2");
        System.out.println("Пример: x+12+y*2");
        System.out.print("Введите выражение: ");

        String userExpression = cin.nextLine().trim();

        try {
            Map<String, Double> userVariables = requestVariableValues(userExpression, cin);
            double result = MathExpression.calculate(userExpression, userVariables);
            System.out.printf("Результат вычисления: %.4f%n", result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода");
        } catch (ArithmeticException e) {
            System.out.println("Арифметическая ошибка");
        }
        cin.close();
    }

    /**
     * Запрашивает значения переменных у пользователя.
     *
     * @param expression строка с математическим выражением
     * @param input сканер для ввода значений
     * @return map значений переменных
     */
    private static Map<String, Double> requestVariableValues(String expression, Scanner input) {
        Map<String, Double> variables = new HashMap<>();
        char[] expressionChars = expression.toCharArray();

        for (int i = 0; i < expressionChars.length; i++) {
            if (Character.isLetter(expressionChars[i])) {
                StringBuilder varNameBuilder = new StringBuilder();

                while (i < expressionChars.length && Character.isLetter(expressionChars[i])) {
                    varNameBuilder.append(expressionChars[i++]);
                }
                i--;

                String variableName = varNameBuilder.toString();

                if (!variables.containsKey(variableName) &&
                        !MathExpression.isFunction(variableName)) {

                    System.out.printf("Введите значение для '%s': ", variableName);
                    while (!input.hasNextDouble()) {
                        System.out.println("Ошибка: введите числовое значение");
                        input.next();
                        System.out.printf("Введите значение для '%s': ", variableName);
                    }
                    variables.put(variableName, input.nextDouble());
                    input.nextLine();
                }
            }
        }
        return variables;
    }
}