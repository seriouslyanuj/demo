package com.luckpay.demo.Repo;

import com.luckpay.demo.Model.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, String> {
}
