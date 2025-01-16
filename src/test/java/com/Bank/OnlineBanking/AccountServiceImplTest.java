package com.Bank.OnlineBanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Bank.OnlineBanking.Notification.EmailService;
import com.Bank.OnlineBanking.Notification.SmsService;
import com.Bank.OnlineBanking.dto.TransferAmountDto;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.repository.AccountRepository;
import com.Bank.OnlineBanking.repository.TransactionRepository;
import com.Bank.OnlineBanking.servicesImpl.AccountServiceImpl;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SmsService smsService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account sourceAccount;
    private Account destinationAccount;
    private TransferAmountDto transferAmountDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sourceAccount = new Account();
        sourceAccount.setAccountNumber("SBI12345");
        sourceAccount.setBalance(1000.0);

        destinationAccount = new Account();
        destinationAccount.setAccountNumber("SBI67890");
        destinationAccount.setBalance(500.0);

        transferAmountDto = new TransferAmountDto();
        transferAmountDto.setSourceAccountNumber("SBI12345");
        transferAmountDto.setDestinationAccountNumber("SBI67890");
        transferAmountDto.setAmount(200.0);
    }

    @Test
    void testTransferAmount_Success() {
        when(accountRepository.getAccountByAccountNumber("SBI12345")).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.getAccountByAccountNumber("SBI67890")).thenReturn(Optional.of(destinationAccount));

        String result = accountService.transferAmount(transferAmountDto);

        assertEquals("Transaction Successful", result);
        assertEquals(800.0, sourceAccount.getBalance());
        assertEquals(700.0, destinationAccount.getBalance());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(smsService, times(1)).sendSms(anyString(), anyString());
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testTransferAmount_InsufficientBalance() {
        transferAmountDto.setAmount(1500.0);  // Insufficient funds

        when(accountRepository.getAccountByAccountNumber("SBI12345")).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.getAccountByAccountNumber("SBI67890")).thenReturn(Optional.of(destinationAccount));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.transferAmount(transferAmountDto);
        });

        assertEquals("Insufficient Balance in source account : SBI12345", exception.getMessage());

        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(smsService, times(0)).sendSms(anyString(), anyString());
        verify(emailService, times(0)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testTransferAmount_AccountNotFound() {
        when(accountRepository.getAccountByAccountNumber("SBI12345")).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.transferAmount(transferAmountDto);
        });

        assertEquals("Source Account SBI12345 is not available", exception.getMessage());

        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(smsService, times(0)).sendSms(anyString(), anyString());
        verify(emailService, times(0)).sendEmail(anyString(), anyString(), anyString());
    }
}

