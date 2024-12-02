package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AccountReportController implements Initializable, DataAccessLayer {

	@FXML private ChoiceBox<String> accountOfReport;
	@FXML private TableColumn<Transaction, String> transactionTypeCol;
	@FXML private TableColumn<Transaction, String> transactionDateCol;
	@FXML private TableColumn<Transaction, String> transactionDescriptionCol;
	@FXML private TableColumn<Transaction, String> paymentAmountCol;
	@FXML private TableColumn<Transaction, String> depositAmountCol;
	@FXML private TableView<Transaction> transactionTable;
	private String currentAccount;
	private ObservableList<Transaction> transactionList;
	Transaction selectedTransaction;

	public void initialize(URL url, ResourceBundle resourceBundle) {

		transactionTypeCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionType"));
		transactionDateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDate"));
		transactionDescriptionCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("transactionDescription"));
		paymentAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("paymentAmount"));
		depositAmountCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("depositAmount"));

		try {
			populateAccountMenu("Accounts.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}

		transactionList = FXCollections.observableArrayList();

		
		accountOfReport.setValue(accountOfReport.getItems().get(0));
		currentAccount = accountOfReport.getItems().get(0);
		transactionList = loadTransactionDataByAccount(accountOfReport.getValue());


		/*
		accountOfReport.setValue(accountOfReport.getItems().get(0));
		transactionList = loadTransactionDataByAccount(accountOfReport.getValue());
		*/
		
		transactionTable.setItems(transactionList);

		//sets to descending order by date
		transactionDateCol.setSortType(TableColumn.SortType.DESCENDING);
		transactionTable.getSortOrder().add(transactionDateCol);
		transactionTable.sort();

		//listener for when the combobox changes
		accountOfReport.valueProperty().addListener((observable, oldAccount, newAccount) -> {
			if(newAccount != null) {
				transactionTable.setItems(loadTransactionDataByAccount(accountOfReport.getValue()));
				currentAccount = accountOfReport.getValue();
			}
		});

		//TODO: Add a listener for whenever a user clicks a transaction which switches to a read-only page with the transaction details
		transactionTable.setOnMouseClicked((MouseEvent event) -> {
			if(event.getClickCount() == 1) {
				selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
			}
		});


	}

	public void populateAccountMenu(String filepath) throws IOException {
		File file = new File(filepath);
		if(file.exists()) {
			//populate table
			BufferedReader br = new BufferedReader(new FileReader("Accounts.csv"));
			String line;
			while((line = br.readLine()) != null) {
				accountOfReport.getItems().add((line.split(","))[0]);
			}
		}
		else {
			return;
		}
	}

	public void setAccount(String account) {
		this.currentAccount = account;
		accountOfReport.setValue(accountOfReport.getItems().get(accountOfReport.getItems().indexOf(account)));
		transactionList = loadTransactionDataByAccount(accountOfReport.getValue());
	}

	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToReadOnlyTransactionScene(ActionEvent event) throws IOException {

		if(selectedTransaction == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please select a transaction to view!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}



		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadOnlyTransactionScene.fxml"));
		Parent root = loader.load();
		ReadOnlyTransactionController controller = loader.getController();
		controller.setTransaction(currentAccount, selectedTransaction.getTransactionType(), selectedTransaction.getTransactionDate(), selectedTransaction.getTransactionDescription(), selectedTransaction.getPaymentAmount(), selectedTransaction.getDepositAmount());
		controller.setAccount(currentAccount);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();


	}

}
