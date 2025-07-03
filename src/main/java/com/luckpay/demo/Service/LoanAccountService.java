package com.luckpay.demo.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckpay.demo.DTO.ExternalLoanAccountResponse;
import com.luckpay.demo.Model.LoanAccount;
import com.luckpay.demo.Repo.LoanAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class LoanAccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoanAccountRepository repository;

    public LoanAccount fetchAndStoreLoanAccount(String accountId) {
        String url = "http://demo9993930.mockable.io/loanaccount/" + accountId;

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            // Check if response content is JSON
            String contentType = responseEntity.getHeaders().getContentType().toString();
            if (!contentType.contains("application/json")) {
                return null;
            }

            // Convert JSON string to ExternalLoanAccountResponse
            ObjectMapper mapper = new ObjectMapper();
            ExternalLoanAccountResponse response = mapper.readValue(responseEntity.getBody(), ExternalLoanAccountResponse.class);

            if (response == null || response.getEmiDetails() == null) {
                return null;
            }

            // Find the first due EMI
            for (ExternalLoanAccountResponse.EmiDetail emi : response.getEmiDetails()) {
                if (emi.isDueStatus()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
                    YearMonth ym = YearMonth.parse(emi.getMonth(), formatter);
                    LocalDate dueDate = ym.atDay(1);

                    LoanAccount loan = new LoanAccount(
                            response.getLoanAccountNumber(),
                            dueDate,
                            emi.getEmiAmount()
                    );

                    return repository.save(loan);
                }
            }

        } catch (Exception e) {
            // Optional: log the exception
            System.out.println("Error fetching or parsing loan account data: " + e.getMessage());
        }

        return null; // fallback when no due EMI or an error occurred
    }
}
