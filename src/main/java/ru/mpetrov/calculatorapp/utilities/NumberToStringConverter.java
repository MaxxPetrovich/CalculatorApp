package ru.mpetrov.calculatorapp.utilities;

import java.util.*;
import ru.mpetrov.calculatorapp.exceptions.WrongInputException;


public class NumberToStringConverter {
    public static String convert(long value) throws WrongInputException {
        if (value < 0) {
            throw new WrongInputException("Отрицательный резкльтат");
        }
        String result = "";

        Map<Long, String> digits = new HashMap<>();
        digits.put(0L, "ноль");
        digits.put(1L, "один");
        digits.put(2L, "два");
        digits.put(3L, "три");
        digits.put(4L, "четыре");
        digits.put(5L, "пять");
        digits.put(6L, "шесть");
        digits.put(7L, "семь");
        digits.put(8L, "восемь");
        digits.put(9L, "девять");

        Map<Long, String> tenToNineteen = new HashMap<>();
        tenToNineteen.put(0L, "десять");
        tenToNineteen.put(1L, "одиннадцать");
        tenToNineteen.put(2L, "двенадцать");
        tenToNineteen.put(3L, "тринадцать");
        tenToNineteen.put(4L, "четырнадцать");
        tenToNineteen.put(5L, "пятнадцать");
        tenToNineteen.put(6L, "шестнадцать");
        tenToNineteen.put(7L, "семнадцать");
        tenToNineteen.put(8L, "восемнадцать");
        tenToNineteen.put(9L, "девятнадцать");

        Map<Long, String> decimals = new HashMap<>();
        decimals.put(2L, "двадцать");
        decimals.put(3L, "тридцать");
        decimals.put(4L, "сорок");
        decimals.put(5L, "пятьдесят");
        decimals.put(6L, "шестьдесят");
        decimals.put(7L, "семьдесят");
        decimals.put(8L, "восемьдесят");
        decimals.put(9L, "девяносто");

        if (value / 10 < 1) result = digits.get(value % 10);
        if (value / 10 == 1) result = tenToNineteen.get(value % 10);
        if (value / 10 > 1 && value % 10 == 0) result = decimals.get(value / 10);
        if (value / 10 > 1 && value % 10 != 0) result = decimals.get(value / 10) + " " + digits.get(value % 10);
        return result;
    }
}
