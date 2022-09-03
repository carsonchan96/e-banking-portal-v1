package com.demo.ebankingportal.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.demo.ebankingportal.models.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID>{
    @Query(value = "SELECT * FROM transactions where account_id in :account_ids", nativeQuery = true)
    Page<Transaction> findAllByAccountId(@Param("account_ids")List<Long> account_ids, Pageable pageable);
}
