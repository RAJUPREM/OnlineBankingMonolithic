package com.Bank.OnlineBanking.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    public Account(Long id, String accountNumber, Double balance, User user, Set<Transaction> sentTransactions,
			Set<Transaction> receivedTransactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.user = user;
		this.sentTransactions = sentTransactions;
		this.receivedTransactions = receivedTransactions;
	}

	public Account() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private Double balance;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private Set<Transaction> sentTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private Set<Transaction> receivedTransactions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Transaction> getSentTransactions() {
		return sentTransactions;
	}

	public void setSentTransactions(Set<Transaction> sentTransactions) {
		this.sentTransactions = sentTransactions;
	}

	public Set<Transaction> getReceivedTransactions() {
		return receivedTransactions;
	}

	public void setReceivedTransactions(Set<Transaction> receivedTransactions) {
		this.receivedTransactions = receivedTransactions;
	}

	@Override
	public String toString() {
	    return "Account [id=" + id + 
	           ", accountNumber=" + accountNumber + 
	           ", balance=" + balance + 
	           ", user=" + (user != null ? user.getId() : null) + "]";
	}
}
