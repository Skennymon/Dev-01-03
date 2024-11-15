package application;

public class ScheduleTransactionBean extends Transaction {
	
	private String scheduleName;
	private String frequency;
	private String dueDate;
	
	public ScheduleTransactionBean(String scheduleName, String account, String transactionType, String frequency, String dueDate, String paymentAmount) {
		super(account, transactionType, paymentAmount);
		this.setScheduleName(scheduleName);
		this.setFrequency(frequency);
		this.setDueDate(dueDate);
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
}
