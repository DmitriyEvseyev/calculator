package com.neoflex.calculator.utils;

import com.neoflex.calculator.model.LoanOffer;
import com.neoflex.calculator.model.LoanStatementRequest;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {
    private final ModelMapper modelMapper;

    @Autowired
    public Converter() {
        this.modelMapper = new ModelMapper();
    }

    public LoanStatementRequest convertLoanStatementRequestDtoToLoanStatementRequest(
            LoanStatementRequestDto loanStatementRequestDto) {
        return modelMapper.map(loanStatementRequestDto, LoanStatementRequest.class);
    }

    public List<LoanOfferDto> convertListLoanOfferToListLoanOfferDto(List<LoanOffer> loanOffers) {
        return loanOffers.stream()
                .map(x -> modelMapper.map(x, LoanOfferDto.class))
                .collect(Collectors.toList());
    }
}
