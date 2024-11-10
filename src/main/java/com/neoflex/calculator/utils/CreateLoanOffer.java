package com.neoflex.calculator.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Component
public class CreateLoanOffer {
    @Value("${application.bank.interestrate}")
    public BigDecimal annualInterestRate;

    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    public BigDecimal getTotalAmount(BigDecimal requestedAmount,
                                     Integer term,
                                     Boolean isInsuranceEnabled,
                                     Boolean isSalaryClient) {
        // todo уточнить как влияеет (расчет) страховка и зарплатный клиент на TotalAmount
        return getMonthlyPayment(requestedAmount, term).multiply(new BigDecimal(term * 12))
                .add(getAmountOfIsInsuranceEnabled(isInsuranceEnabled))
                .add(getAmountOfIsSalaryClient(isSalaryClient));
    }

    public BigDecimal getAmountOfIsInsuranceEnabled(Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? new BigDecimal("2000") : new BigDecimal("0");
    }

    public BigDecimal getAmountOfIsSalaryClient(Boolean isSalaryClient) {
        return isSalaryClient ? new BigDecimal("-1000") : new BigDecimal("0");
    }


//    M = P ⋅ r(1 + r)ⁿ / (1 + r)ⁿ - 1
// numerator -     P ⋅ r(1 + r)ⁿ
// denominator -   (1 + r)ⁿ - 1
//    где:  M  — ежемесячный платеж,
//  P  — сумма кредита (основной долг),
//  r  — месячная процентная ставка (годовая ставка деленная на 12 и переведенная в десятичную форму),
//  n  — общее количество платежей (количество месяцев).

    public BigDecimal getMonthlyPayment(BigDecimal requestedAmount, Integer term) {
        // Расчет ежемесячной процентной ставки
        BigDecimal monthlyInterestRate = annualInterestRate.divide(new BigDecimal("1200"), 5, RoundingMode.HALF_UP);
        // Общее количество платежей, учитывая, что term в годах
        int totalPayments = term * 12;

        BigDecimal numerator = requestedAmount.multiply(monthlyInterestRate).multiply(BigDecimal.ONE.add(monthlyInterestRate).pow(totalPayments));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyInterestRate).pow(totalPayments).subtract(BigDecimal.ONE);
        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        return monthlyPayment;
        // todo уточнить точность округления monthlyInterestRate, monthlyPayment
    }
}
