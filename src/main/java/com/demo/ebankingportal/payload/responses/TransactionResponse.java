package com.demo.ebankingportal.payload.responses;

import java.util.List;

import com.demo.ebankingportal.dto.TransactionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private List<TransactionDto> transactionRecoreds;
    private float totalCredit;
    private float totalDebit;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
