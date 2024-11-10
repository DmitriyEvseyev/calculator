package com.neoflex.calculator.controller;

import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = calculatorService.calculateOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity<List<LoanOfferDto>> calculateOffersCalc(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = new ArrayList<>();

        // todo написать

        return ResponseEntity.ok(offers);
    }
}