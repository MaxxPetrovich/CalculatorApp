package ru.mpetrov.calculatorapp.utilities;

import ru.mpetrov.calculatorapp.exceptions.DivisionByZeroException;
import ru.mpetrov.calculatorapp.exceptions.TooBigException;
import ru.mpetrov.calculatorapp.exceptions.WrongInputException;

public interface Calculator {
    void add(char x) throws WrongInputException;
    String calc() throws TooBigException, DivisionByZeroException, WrongInputException;
}