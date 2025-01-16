package com.Bank.OnlineBanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Bank.OnlineBanking.Notification.EmailService;
import com.Bank.OnlineBanking.Notification.SmsService;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.ScheduleTransaction;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.repository.AccountRepository;
import com.Bank.OnlineBanking.repository.TransactionRepository;
import com.Bank.OnlineBanking.servicesImpl.ScheduleTransactionServiceImpl;

public class ScheduleTransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SmsService smsService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ScheduleTransactionServiceImpl scheduleTransactionService;

    private Account sourceAccount;
    private Account destinationAccount;
    private ScheduleTransaction scheduleTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Set up source and destination accounts
        sourceAccount = new Account();
        sourceAccount.setAccountNumber("SBI1001");
        sourceAccount.setBalance(5000.00);

        destinationAccount = new Account();
        destinationAccount.setAccountNumber("SBI1002");
        destinationAccount.setBalance(1000.00);

        // Set up a sample scheduled transaction
        scheduleTransaction = new ScheduleTransaction();
        scheduleTransaction.setSourceAccountNumber("SBI1001");
        scheduleTransaction.setDestinationAccountNumber("SBI1002");
        scheduleTransaction.setAmount(1000.00);
        scheduleTransaction.setScheduledTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        scheduleTransaction.setStatus("PENDING");
    }

    @Test
    public void testTransferAmountScheduled_Success() {
        // Arrange
        when(accountRepository.getAccountByAccountNumber(scheduleTransaction.getSourceAccountNumber()))
            .thenReturn(Optional.of(sourceAccount));
        when(accountRepository.getAccountByAccountNumber(scheduleTransaction.getDestinationAccountNumber()))
            .thenReturn(Optional.of(destinationAccount));

        // Act
        String result = scheduleTransactionService.transferAmountScheduled(scheduleTransaction);

        // Assert
        assertEquals("Transaction Scheduled at :" + scheduleTransaction.getScheduledTime(), result);
        verify(accountRepository, times(2)).save(any(Account.class)); // Verifying if the accounts are saved
        verify(transactionRepository, times(1)).save(any(Transaction.class)); // Verifying if transaction is saved
        verify(smsService, times(1)).sendSms(anyString(), anyString()); // Verifying if SMS is sent
    }

    @Test
    public void testTransferAmountScheduled_InsufficientBalance() {
        // Arrange
        sourceAccount.setBalance(500.00); // Set insufficient balance for the transaction
        when(accountRepository.getAccountByAccountNumber(scheduleTransaction.getSourceAccountNumber()))
            .thenReturn(Optional.of(sourceAccount));
        when(accountRepository.getAccountByAccountNumber(scheduleTransaction.getDestinationAccountNumber()))
            .thenReturn(Optional.of(destinationAccount));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            scheduleTransactionService.transferAmountScheduled(scheduleTransaction);
        });

        assertEquals("Insufficient Balance in source account : SBI1001", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class)); // Verifying accounts were not saved
        verify(transactionRepository, never()).save(any(Transaction.class)); // Verifying transaction was not saved
    }

    @Test
    public void testCashDepositeScheduled_Success() {
        // Arrange
        when(accountRepository.getAccountByAccountNumber(scheduleTransaction.getSourceAccountNumber()))
            .thenReturn(Optional.of(sourceAccount));

        // Act
        String result = scheduleTransactionService.cashDepositeScheduled(scheduleTransaction);

        // Assert
        assertEquals("Deposited Scheduled at :" + scheduleTransaction.getScheduledTime(), result);
        verify(accountRepository, times(1)).save(any(Account.class)); // Verifying if the account is saved
        verify(transactionRepository, times(1)).save(any(Transaction.class)); // Verifying if transaction is saved
        verify(smsService, times(1)).sendSms(anyString(), anyString()); // Verifying if SMS is sent
    }

    @Test
    public void testProcessScheduledTransactions_Success() {
        // Arrange
        when(transactionRepository.getTransactionsByTransactionStatusAndCurrentTime(anyString(), anyString()))
            .thenReturn(Optional.of(List.of(new Transaction())));

        // Act
        scheduleTransactionService.proccessScheduledTransactions();

        // Assert
        verify(transactionRepository, atLeastOnce()).save(any(Transaction.class)); // Verifying if any transactions are saved
    }

    @Test
    public void testProcessScheduledTransactions_NoPendingTransactions() {
        // Arrange
        when(transactionRepository.getTransactionsByTransactionStatusAndCurrentTime(anyString(), anyString()))
            .thenReturn(Optional.of(List.of()));

        // Act
        scheduleTransactionService.proccessScheduledTransactions();

        // Assert
        verify(transactionRepository, never()).save(any(Transaction.class)); // Verifying no transactions were saved
    }
}

