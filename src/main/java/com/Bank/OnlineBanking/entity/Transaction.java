package com.Bank.OnlineBanking.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

	public Transaction(Long id, Account fromAccount, Account toAccount, Double amount, String type,
			String transactionDate, String status) {
		super();
		this.id = id;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.type = type;
		this.transactionDate = transactionDate;
		this.status = status;
	}

	public Transaction() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonManagedReference
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @JsonManagedReference
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @Column(nullable = false)
    private Double amount;
    
    @Column(nullable = false)
    private String type; // "TRANSFER", "SCHEDULED"

    @Column(nullable = false)
    private String transactionDate;

    @Column(nullable = false)
    private String status; // "PENDING", "COMPLETED", "FAILED"

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount="
				+ amount + ", type=" + type + ", transactionDate=" + transactionDate + ", status=" + status + "]";
	}

}
