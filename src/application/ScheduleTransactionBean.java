package application;

public class ScheduleTransactionBean extends Transaction {
	
	private String scheduleName;
	private String frequency;
	private String dueDate;
	
	public ScheduleTransactionBean(String account, String transactionType, String paymentAmount, String scheduleName, String frequency, String dueDate) {
		super(account, transactionType, paymentAmount);
		this.scheduleName = scheduleName;
		this.frequency = frequency;
		this.dueDate = dueDate;
	}
	
}
