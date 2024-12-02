package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public interface DataAccessLayer {

	default void saveTransactionTypeInfo(String transactionType) {
		try (FileWriter writer = new FileWriter("TransactionType.csv", true)) {
			writer.write(transactionType + "\n");
			writer.flush();
			System.out.println("Data Saved to TransactionType.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void saveTransactionInfo(String account, String transactionType, LocalDate transactionDate, String transactionDescription, String paymentAmount, String depositAmount) {
		try (FileWriter writer = new FileWriter("Transactions.csv", true)) {
			writer.write(account + "," + transactionType + "," + transactionDate + "," + transactionDescription + "," + paymentAmount + "," + depositAmount + " " + "\n"); //fixed bug
			writer.flush();
			System.out.println("Data Saved to Transactions.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//the difference between saveTransactionInfo and editSaveTransactionInfo is that editSaveTransactionInfo OVERWRITES Transactions.csv instead of appending to it
	default void editSaveTransactionInfo(String account, String transactionType, LocalDate transactionDate, String transactionDescription, String paymentAmount, String depositAmount, ObservableList<Transaction> transactionList, Transaction editedTransaction) {
		try (FileWriter writer = new FileWriter("Transactions.csv")) {
			
			for(int i = 0; i < transactionList.size(); i++) {
				if(transactionList.get(i).equals(editedTransaction)) {
					continue;
				}
				else {
					writer.write(transactionList.get(i).getAccount() + "," + transactionList.get(i).getTransactionType() + "," + transactionList.get(i).getTransactionDate() + "," + transactionList.get(i).getTransactionDescription() + "," + transactionList.get(i).getPaymentAmount() + "," + transactionList.get(i).getDepositAmount() + "," + " " + "\n");
				}
			}
			
			writer.write(account + "," + transactionType + "," + transactionDate + "," + transactionDescription + "," + paymentAmount + "," + depositAmount + " " + "\n"); //fixed bug
			writer.flush();
			System.out.println("Data Saved to Transactions.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	default void editSaveScheduleTransactionInfo(String scheduleName, String account, String transactionType, String frequency, String dueDate, String paymentAmount, ScheduleTransactionBean editedScheduledTransaction, ObservableList<ScheduleTransactionBean> scheduledTransactionList) {
		try(FileWriter writer = new FileWriter("ScheduledTransactions.csv")) {
			
			for(int i = 0; i < scheduledTransactionList.size(); i++) {
				if(scheduledTransactionList.get(i).equals(editedScheduledTransaction)) {
					continue;
				}
				else {
					writer.write(scheduledTransactionList.get(i).getScheduleName() + "," + scheduledTransactionList.get(i).getAccount() + "," + scheduledTransactionList.get(i).getTransactionType() + "," + scheduledTransactionList.get(i).getFrequency() + "," + scheduledTransactionList.get(i).getDueDate() + "," + scheduledTransactionList.get(i).getPaymentAmount() + "\n");
				}
			}
			writer.write(scheduleName + "," + account + "," + transactionType + "," + frequency + "," + dueDate + "," + paymentAmount + "\n");
			System.out.println("Data Saved to ScheduledTransactions.csv");
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// reads all the accounts saved in the csv file and returns a list
	default ObservableList<Account> loadAccountData() {

		ObservableList<Account> accountList = FXCollections.observableArrayList();
		String filePath = "Accounts.csv";

		try {
			BufferedReader br = new BufferedReader(new FileReader("Accounts.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] account = line.split(",");
				accountList.add(new Account(account[0], account[1], account[2]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please add some accounts!");
			Optional<ButtonType> result = alert.showAndWait();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return accountList;
	}
	
	//returns a list of transaction objects
	default ObservableList<Transaction> loadTransactionData() {
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		String filePath = "Accounts.csv";

		try {
			BufferedReader br = new BufferedReader(new FileReader("Transactions.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] transaction = line.split(",");
				transactionList.add(new Transaction(transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please add some transactions!");
			Optional<ButtonType> result = alert.showAndWait();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return transactionList;
	}
	
	default ObservableList<Transaction> loadTransactionDataByAccount(String account) {
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Transactions.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] transaction = line.split(",");
				
				if(transaction[0].equals(account)) {
					transactionList.add(new Transaction(transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5]));
				}
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			
		}
		catch (IOException e) {
			
		}
		
		
		
		return transactionList;
	}
	
	//overloading some crap
	default ObservableList<Transaction> loadTransactionDataByTransactionType(String transactionType) {
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("Transactions.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] transaction = line.split(",");
				
				if(transaction[1].equals(transactionType)) {
					transactionList.add(new Transaction(transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5]));
				}
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			
		}
		catch (IOException e) {
			
		}
		
		
		
		return transactionList;
		
		
	}
	
	default void saveAccountInfo(String name, LocalDate date, String balance) {
		try (FileWriter writer = new FileWriter("Accounts.csv", true)) {
			writer.write(name + "," + date.toString() + "," + balance + "\n");
			writer.flush();
			System.out.println("Data Saved to Accounts.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	default void saveScheduleTransactionInfo(String scheduleName, String account, String transactionType, String frequency, String dueDate, String paymentAmount) {
		try (FileWriter writer = new FileWriter("ScheduledTransactions.csv", true)) {
			writer.write(scheduleName + "," + account + "," + transactionType + "," + frequency + "," + dueDate + "," + paymentAmount + "\n");
			writer.flush();
			System.out.println("Data Saved to ScheduledTransactions.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	default boolean checkForDuplicates(String fileName, String name) throws IOException {
		File file = new File(fileName);
		String line;
		String theLine;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		while((line = br.readLine()) != null) {
			theLine = line.split(",")[0];
			if(theLine.toLowerCase().equals(name.toLowerCase())) {
				return true;
			}
		}


		return false;
	}
	
	//returns a list of ScheduleTransactionBean objects 
	default ObservableList<ScheduleTransactionBean> loadScheduledTransactionsInfo() {
		ObservableList<ScheduleTransactionBean> scheduledTransactionList = FXCollections.observableArrayList();

		try {
			BufferedReader br = new BufferedReader(new FileReader("ScheduledTransactions.csv"));
			String line;
			while((line = br.readLine()) != null) {
				String[] transaction = line.split(",");
				scheduledTransactionList.add(new ScheduleTransactionBean(transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5]));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please add some transactions!");
			Optional<ButtonType> result = alert.showAndWait();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return scheduledTransactionList;
		
	}
	

}
