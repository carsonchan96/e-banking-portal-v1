package com.demo.ebankingportal.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.demo.ebankingportal.dto.TransactionDto;
// import com.demo.ebankingportal.kafka.JsonKafkaProducer;
import com.demo.ebankingportal.models.ETranType;
import com.demo.ebankingportal.models.Transaction;
import com.demo.ebankingportal.models.User;
import com.demo.ebankingportal.models.Account;
import com.demo.ebankingportal.payload.responses.TransactionResponse;
import com.demo.ebankingportal.repositories.TransactionRepository;
import com.demo.ebankingportal.repositories.UserRepository;

@Service
public class TransactionService {
    @Autowired
    CurrencyService currencyService;

    @Autowired
    UserRepository userRepository;

    private TransactionRepository transactionRepository;

    @Value("${currency.Base}")
    private String target_currency;

    @Autowired
    // private JsonKafkaProducer jsonKafkaProducer;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse getAllTransactions(int pageNo, int pageSize, String sortBy, String sortDir, String username) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        List<Long> uidList = new ArrayList<>();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        user.getAccounts().forEach((Account ac)->{
            uidList.add(ac.getId());
        });

        Page<Transaction> transactions = transactionRepository.findAllByAccountId(uidList, pageable);

        List<Transaction> listOfTransactions = transactions.getContent();

        List<TransactionDto> content = listOfTransactions.stream().map(transaction -> mapToDto(transaction))
                .collect(Collectors.toList());

        Float c = 0f;
        Float d = 0f;

        for (TransactionDto item : content) {
            if (item.getType() == ETranType.TRANS_IN) {
                c = c + currencyService.currencyTransform(item.getCurrency(), target_currency, item.getAmount(), java.time.LocalDate.now().toString());
            } else {
                d = d + currencyService.currencyTransform(item.getCurrency(), target_currency, item.getAmount(), java.time.LocalDate.now().toString());
            }

        }

        // transform to String
        // System.out.println(flaotToString(c));
        // System.out.println(flaotToString(d));

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTotalCredit(c);
        transactionResponse.setTotalDebit(d);
        transactionResponse.setTransactionRecoreds(content);
        transactionResponse.setPageNo(transactions.getNumber());
        transactionResponse.setPageSize(transactions.getSize());
        transactionResponse.setTotalElements(transactions.getTotalElements());
        transactionResponse.setTotalPages(transactions.getTotalPages());
        transactionResponse.setLast(transactions.isLast());
        
        // jsonKafkaProducer.sendTransactionMessage(transactionResponse);

        return transactionResponse;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setAccount(transaction.getAccount().getAccountNo());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCurrency(transaction.getCurrency());
        transactionDto.setType(transaction.getType());
        transactionDto.setIban(transaction.getIban());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setExec_date(transaction.getCreatedAt());
        return transactionDto;
    }

    private String flaotToString(double value) //Got here 6.743240136E7 or something..
    {
        DecimalFormat formatter;

        if(value - (int)value > 0.0)
            formatter = new DecimalFormat("0.00"); //Here you can also deal with rounding if you wish..
        else
            formatter = new DecimalFormat("0");

        return formatter.format(value);
    }
}
