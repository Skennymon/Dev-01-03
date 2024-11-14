package application;

public class Transaction {
	
	private String account;
	private String transactionType;
	private String transactionDate;
	private String transactionDescription;
	private String paymentAmount;
	private String depositAmount;
	
	public Transaction(String account, String transactionType, String transactionDate, String transactionDescription, String paymentAmount, String depositAmount) {
		this.setAccount(account);
		this.setTransactionType(transactionType);
		this.setTransactionDate(transactionDate);
		this.setTransactionDescription(transactionDescription);
		this.setPaymentAmount(paymentAmount);
		this.setDepositAmount(depositAmount);
		
	}
	
	public Transaction(String account, String transactionType, String paymentAmount) {
		this.account = account;
		this.transactionType = transactionType;
		this.paymentAmount = paymentAmount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	
}
