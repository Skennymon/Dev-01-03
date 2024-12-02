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
		
		transactionList = loadTransactionDataByTransactionType(currentTransactionType);

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
		
		transactionTable.setOnMouseClicked((MouseEvent event) -> {
			if(event.getClickCount() >= 1) {
				selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
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
	
	public void setTransactionType(String transactionType) {
		this.currentTransactionType = transactionType;
		transactionList = loadTransactionDataByTransactionType(currentTransactionType);
		transactionTable.setItems(transactionList);
		transactionTypeOfReport.setValue(transactionTypeOfReport.getItems().get(transactionTypeOfReport.getItems().indexOf(transactionType)));
	}
	
	public void switchToReadOnlyTransaction2(ActionEvent event) throws IOException {
		if(selectedTransaction == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please select a transaction to view!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}



		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadOnlyTransactionScene2.fxml"));
		Parent root = loader.load();
		ReadOnlyTransactionController2 controller = loader.getController();
		controller.setTransaction(selectedTransaction.getAccount(), currentTransactionType, selectedTransaction.getTransactionDate(), selectedTransaction.getTransactionDescription(), selectedTransaction.getPaymentAmount(), selectedTransaction.getDepositAmount());
		controller.setTransactionType(currentTransactionType);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
