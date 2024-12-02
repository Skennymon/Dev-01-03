package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReadOnlyTransactionController2 implements Initializable, DataAccessLayer {
	
	private String transactionType;
	private Transaction selectedTransaction;
	@FXML
	private ChoiceBox<String> accountDropdownInput;
	@FXML
	private ChoiceBox<String> transactionTypeDropdownInput;
	@FXML
	private DatePicker transactionDateInput;
	@FXML
	private TextField transactionDescriptionInput;
	@FXML
	private TextField paymentAmountInput;
	@FXML
	private TextField depositAmountInput;
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		//this prevents the user from changing the fields
		accountDropdownInput.setDisable(true);
		transactionTypeDropdownInput.setDisable(true);
		transactionDateInput.setDisable(true);
		transactionDescriptionInput.setDisable(true);
		paymentAmountInput.setDisable(true);
		depositAmountInput.setDisable(true);
		
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public void setTransaction(String account, String transactionType, String date, String transactionDescription, String paymentAmount, String depositAmount) {
		accountDropdownInput.setValue(account);
		transactionTypeDropdownInput.setValue(transactionType);
		transactionDateInput.setValue(LocalDate.parse(date));
		transactionDescriptionInput.setText(transactionDescription.trim());
		paymentAmountInput.setText(paymentAmount.trim());
		depositAmountInput.setText(depositAmount.trim()); 
	}
	
	//TODO: Add Switch back to TransactionReportScene
	public void switchTransactionTypeReportScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TransactionTypeReportScene.fxml"));
		Parent root = loader.load();
		TransactionTypeReportController controller = loader.getController();
		controller.setTransactionType(transactionType);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
