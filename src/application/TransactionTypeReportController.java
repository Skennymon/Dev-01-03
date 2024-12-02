package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionTypeReportController implements Initializable, DataAccessLayer {

	@FXML ChoiceBox<String> transactionTypeOfReport;

	@FXML private TableView<Transaction> transactionTable;

	@FXML private TableColumn<Transaction, String> accountCol;

	@FXML private TableColumn<Transaction, String> transactionDateCol;

	@FXML private TableColumn<Transaction, String> transactionDescriptionCol;

	@FXML private TableColumn<Transaction, String> paymentAmountCol;

	@FXML private TableColumn<Transaction, String> depositAmountCol;

	private ObservableList<Transaction> transactionList;
	Transaction selectedTransaction;
	private String currentTransactionType;

	public void initialize(URL url, ResourceBundle resourceBundle) {

		accountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("account"));
		transactionDateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDate"));
		transactionDescriptionCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDescription"));
		paymentAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("paymentAmount"));
		depositAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("depositAmount"));
		try {
			populateTransactionMenu("TransactionType.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

		transactionList = FXCollections.observableArrayList();
		transactionTypeOfReport.setValue(transactionTypeOfReport.getItems().get(0));
		currentTransactionType = transactionTypeOfReport.getItems().get(0);

		transactionTable.setItems(transactionList);
		
		//sets to descending order by date
		transactionDateCol.setSortType(TableColumn.SortType.DESCENDING);
		transactionTable.getSortOrder().add(transactionDateCol);
		transactionTable.sort();
		
		//listener when transactionType changes
		transactionTypeOfReport.valueProperty().addListener((observable, oldTransactionType, newTransactionType) -> {
			if(newTransactionType != null) {
				transactionTable.setItems(loadTransactionDataByTransactionType(transactionTypeOfReport.getValue())); //TODO: add loadTransactionDataByTransactionType() method
				currentTransactionType = transactionTypeOfReport.getValue();
			}
		});

	}

	public void populateTransactionMenu(String filepath) throws IOException {
		File file = new File(filepath);
		if(file.exists()) {
			//populate table						
			BufferedReader br = new BufferedReader(new FileReader("TransactionType.csv"));
			String line;
			while((line = br.readLine()) != null) {
				transactionTypeOfReport.getItems().add(line);
			}
		}
		else {
			return;
		}
	}

}
