package com.luckpay.demo.Controller;

import com.luckpay.demo.DTO.ExternalLoanAccountResponse;
import com.luckpay.demo.Model.LoanAccount;
import com.luckpay.demo.Service.LoanAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loanaccount")
public class LoanAccountController{
    @Autowired
    private LoanAccountService service;

    @GetMapping("/{id}")
    public ResponseEntity<LoanAccount> getLoanAccount(@PathVariable String id) {
        LoanAccount response = service.fetchAndStoreLoanAccount(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
