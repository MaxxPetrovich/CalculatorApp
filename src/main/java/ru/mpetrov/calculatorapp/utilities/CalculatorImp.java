package ru.mpetrov.calculatorapp.utilities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import ru.mpetrov.calculatorapp.exceptions.DivisionByZeroException;
import ru.mpetrov.calculatorapp.exceptions.TooBigException;
import ru.mpetrov.calculatorapp.exceptions.WrongInputException;

import java.util.Collections;
import java.util.Stack;

@Service
@RequestScope
public class CalculatorImp implements Calculator {
    private boolean lastTokenIsDigit = false;
    private final String OPERATORS = "+-*/";
    private final Stack<String> stackOperations = new Stack<>();
    private final Stack<String> stackRPN = new Stack<>();
    private final Stack<Double> stackAnswer = new Stack<>();

    private boolean isNumber(char token) {
        return (Character.isDigit(token));
    }

    private boolean isOperator(char token) {
        return OPERATORS.indexOf(token) > -1;
    }

    private byte getPrecedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 2;
    }

    @Override
    public void add(char token) throws WrongInputException {
        if (isOperator(token)) {
            if (lastTokenIsDigit == false) throw new WrongInputException("Два оператора подояд");
            while (!stackOperations.empty()
                    && getPrecedence(String.valueOf(token)) <= getPrecedence(stackOperations
                    .lastElement())) {
                stackRPN.push(stackOperations.pop());
            }
            stackOperations.push(String.valueOf(token));
            lastTokenIsDigit = false;
        } else if (isNumber(token)) {
            if (lastTokenIsDigit) {
                stackRPN.push(String.valueOf((Double.parseDouble(stackRPN.pop()) * 10 + Double.parseDouble(String.valueOf(token)))));
            } else {
                stackRPN.push(String.valueOf(token));
                lastTokenIsDigit = true;
            }
        } else {
            throw new WrongInputException("Не поддерживаемый символ");
        }
    }

    @Override
    public String calc() throws TooBigException, DivisionByZeroException, WrongInputException {
        if (!lastTokenIsDigit) throw new WrongInputException("Выражение закончилось оператором");
        while (!stackOperations.empty()) {
            stackRPN.push(stackOperations.pop());
        }
        Collections.reverse(stackRPN);
        Double p1;
        Double p2;
        while (!stackRPN.empty()) {
            String item = stackRPN.pop();
            switch (item) {
                case "+":
                    p1 = stackAnswer.pop();
                    p2 = stackAnswer.pop();
                    stackAnswer.push((p2 + p1));
                    break;
                case "-":
                    p1 = stackAnswer.pop();
                    p2 = stackAnswer.pop();
                    stackAnswer.push((p2 - p1));
                    break;
                case "*":
                    p1 = stackAnswer.pop();
                    p2 = stackAnswer.pop();
                    stackAnswer.push((p2 * p1));
                    break;
                case "/":
                    p1 = stackAnswer.pop();
                    p2 = stackAnswer.pop();
                    if (p1 == 0.0) throw new DivisionByZeroException("Попытка деления на 0");
                    stackAnswer.push((p2 / p1));
                    break;
                default:
                    stackAnswer.push(Double.parseDouble(item));
            }
        }
        if (stackAnswer.peek() >= 100) throw new TooBigException("Результат превышает 100");
        return NumberToStringConverter.convert(Math.round(stackAnswer.pop()));
    }
}
