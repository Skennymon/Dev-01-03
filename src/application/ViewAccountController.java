package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewAccountController implements Initializable {
	
	private Parent root;
	private Scene scene;
	private Stage stage;
	
	@FXML
	private TableView<Account> accountTableView;
	@FXML
	private TableColumn<Account, String> nameCol;
	@FXML
	private TableColumn<Account, String> openingBalanceCol;
	@FXML
	private TableColumn<Account, String> openingDateCol;

	public void initialize(URL url, ResourceBundle resourceBundle) {
		ObservableList<Account> accountList = FXCollections.observableArrayList();
		
		nameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountName"));
		openingBalanceCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountOpeningBalance"));
		openingDateCol.setCellValueFactory(new PropertyValueFactory<Account, String>("accountOpeningDate"));
		
		accountList = loadAccountData();
		
		accountTableView.setItems(accountList);
	}

	// maybe try opencsv here
	public ObservableList<Account> loadAccountData() {

		ObservableList<Account> accountList = FXCollections.observableArrayList();
		String filePath = "Accounts.csv";
		
		/*
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			while (reader.readLine() != null) {
				String[] line = reader.readLine().split(",");
				accountList.add(new Account(line[0], line[1], line[2]));
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
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
			e.printStackTrace();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		

		for (int i = 0; i < accountList.size(); i++) {
			System.out.println(accountList.get(i).getAccountName());
		}

		System.out.println(accountList.size());
		
		
		
		return accountList;
	}
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
