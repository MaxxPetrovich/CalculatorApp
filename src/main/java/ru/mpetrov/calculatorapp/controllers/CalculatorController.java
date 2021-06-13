package ru.mpetrov.calculatorapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.mpetrov.calculatorapp.exceptions.DivisionByZeroException;
import ru.mpetrov.calculatorapp.exceptions.TooBigException;
import ru.mpetrov.calculatorapp.exceptions.WrongInputException;
import ru.mpetrov.calculatorapp.utilities.Calculator;


@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    private Calculator calculator;

    @Autowired
    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping(value = "/calculate")
    @ResponseBody
    public String Calculate(@RequestParam String input) {
        try {
            input.chars().forEach(c -> {
                try {
                    calculator.add((char) c);
                } catch (WrongInputException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Неверные входные данные", e);
                }
            });
            return calculator.calc();
        } catch (TooBigException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Результат вычисления превышает 100", e);
        } catch (DivisionByZeroException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Попытка деления на 0", e);
        } catch (WrongInputException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Неверные входные данные", e);
        }
    }
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleException(ResponseStatusException exception) {
        return exception.getReason();
    }
}

