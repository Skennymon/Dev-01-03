package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DefineNewTransactionTypeController implements Initializable {
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TextField transactionTypeInput;
	
	public void initialize(URL url, ResourceBundle bundle) {
		
	}
	
	public void save(ActionEvent event) throws IOException {
		
		File file = new File("TransactionType.csv");
		
		if(transactionTypeInput.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please enter the Transaction Type!");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else if(file.exists() && checkForDuplicates()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("That transaction type already exists!");
			Optional<ButtonType> result = alert.showAndWait();
		}
		else {
			saveTransactionTypeInfo(transactionTypeInput.getText());
			Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	public void saveTransactionTypeInfo(String transactionType) {
		try (FileWriter writer = new FileWriter("TransactionType.csv", true)) {
			writer.write(transactionType + "\n");
			writer.flush();
			System.out.println("Data Saved to TransactionType.csv");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkForDuplicates() throws IOException {
		
		String line;
		BufferedReader br = new BufferedReader(new FileReader("TransactionType.csv"));
		while((line = br.readLine()) != null) {
			if(line.toLowerCase().contains(transactionTypeInput.getText().toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
