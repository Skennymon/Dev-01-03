package application;
	
import java.time.LocalDate;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane root = new GridPane();
			GridPane newAcc = new GridPane();
			Scene homeScene = new Scene(root, 1024, 768);
			Scene addNewAccountScene = new Scene(newAcc, 1024, 768);
			
			root.setPadding(new Insets(20, 20, 20, 20));
			root.setVgap(50);
			
			//"Home" text
			Text home = new Text("Home");
			home.setFont(Font.font("Verdana", 50));
			root.add(home, 1, 1);
			
			//buttons for home page
			Button newAccountButton = new Button("Add new Account");
			Button newTransactionButton = new Button("Add new Transaction");
			Button scheduleTransactionButton = new Button("Schedule Transaction");
			Button showAccountButton = new Button("View Accounts");
			Button showTransactionsButton = new Button("View Transactions");
			root.add(newAccountButton, 1, 2);
			root.add(newTransactionButton, 1, 3);
			root.add(scheduleTransactionButton, 1, 4);
			root.add(showAccountButton, 1, 5);
			root.add(showTransactionsButton, 1, 6);
			
			//When you click "Add new Account" it runs this so that the program switches scene the the new account page
			newAccountButton.setOnAction(e -> primaryStage.setScene(addNewAccountScene)); 
			
			//Add New Account Scene customization
			newAcc.setPadding(new Insets(20, 20, 20, 20));
			Text addNewAccountText = new Text("Add New Account");
			addNewAccountText.setFont(Font.font("Verdana", 50));
			GridPane.setConstraints(addNewAccountText, 0, 0);
			
			TextField accountNameInput = new TextField();
			accountNameInput.setPromptText("Input Account Name");
			GridPane.setConstraints(accountNameInput, 0, 1);
			
			//-------
			/*
			TextField accountOpeningDateInput = new TextField();
			accountOpeningDateInput.setPromptText("Input Account Opening Date");
			GridPane.setConstraints(accountOpeningDateInput, 0, 2);
			*/
			DatePicker dp = new DatePicker();
			dp.setValue(LocalDate.now());
			GridPane.setConstraints(dp, 0, 2);
			//-------
			
			TextField accountOpeningBalance = new TextField();
			accountOpeningBalance.setPromptText("Input Account Opening Balance");
			GridPane.setConstraints(accountOpeningBalance, 0, 3);
			
			Button cancelBtn = new Button("Cancel");
			GridPane.setConstraints(cancelBtn, 0, 4);
			
			Button confirmBtn = new Button("Confirm");
			GridPane.setConstraints(confirmBtn, 1, 4);
			
			newAcc.setVgap(50);
			
			newAcc.getChildren().addAll(addNewAccountText, accountNameInput, dp, accountOpeningBalance, confirmBtn, cancelBtn); 
			
			//if the user presses confirm without entering all the fields, then it prompts the user to enter all the fields
			confirmBtn.setOnAction(e -> {
				if(accountNameInput.getText().isEmpty() || accountOpeningBalance.getText().isEmpty()) {
					Text error = new Text("You must enter in all the fields");
					GridPane.setConstraints(error, 0, 5);
					newAcc.getChildren().addAll(error);
				}
				else {
					primaryStage.setScene(homeScene);
				}
			});
			cancelBtn.setOnAction(e -> primaryStage.setScene(homeScene));
			
			//final initialization pls don't touch
			primaryStage.setTitle("Finance Tracker");
			primaryStage.setScene(homeScene);
			primaryStage.show(); 
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
