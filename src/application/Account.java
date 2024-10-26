package application;

import javafx.scene.control.DatePicker;

public class Account {
	
	private String accountName;
	private String accountOpeningDate;
	private String accountOpeningBalance;
	
	public Account(String accountName, String accountOpeningDate, String accountOpeningBalance) {
		this.accountName = accountName;
		this.accountOpeningBalance = accountOpeningBalance;
		this.accountOpeningDate = accountOpeningDate;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountOpeningDate() {
		return accountOpeningDate;
	}

	public void setAccountOpeningDate(String accountOpeningDate) {
		this.accountOpeningDate = accountOpeningDate;
	}

	public String getAccountOpeningBalance() {
		return accountOpeningBalance;
	}

	public void setAccountOpeningBalance(String accountOpeningBalance) {
		this.accountOpeningBalance = accountOpeningBalance;
	}
	
	
	
	
}
