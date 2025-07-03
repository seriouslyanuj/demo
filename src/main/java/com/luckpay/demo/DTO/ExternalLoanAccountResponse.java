package com.luckpay.demo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ExternalLoanAccountResponse {
    private String loanAccountNumber;
    private List<EmiDetail> emiDetails;

    @Data
    public static class EmiDetail {
        private String month;
        private double emiAmount;
        private boolean paidStatus;
        private boolean dueStatus;
    }
}
