package application;

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

public class ViewScheduledTransactionController implements Initializable, DataAccessLayer {

	private Scene scene;
	private Stage stage;

	@FXML
	private TableView<ScheduleTransactionBean> scheduledTransactionTable;
	@FXML
	private TableColumn<ScheduleTransactionBean, String> scheduleNameCol;
	@FXML
	private TableColumn<ScheduleTransactionBean, String> accountCol;
	@FXML
	private TableColumn<ScheduleTransactionBean, String> transactionTypeCol;
	@FXML
	private TableColumn<ScheduleTransactionBean, String> frequencyCol;
	@FXML
	private TableColumn<ScheduleTransactionBean, String> dueDateCol;
	@FXML
	private TableColumn<ScheduleTransactionBean, String> paymentAmountCol;
	

	public void initialize(URL url, ResourceBundle bundle) {
		ObservableList<ScheduleTransactionBean> accountList = FXCollections.observableArrayList();
		
		scheduleNameCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("scheduleName"));
		accountCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("account"));
		transactionTypeCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("transactionType"));
		frequencyCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("frequency"));
		dueDateCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("dueDate"));
		paymentAmountCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("paymentAmount"));

		accountList = loadScheduledTransactionsInfo(); //change to load scheduledTransactionData();
		scheduledTransactionTable.setItems(accountList);

		dueDateCol.setSortType(TableColumn.SortType.ASCENDING);
		scheduledTransactionTable.getSortOrder().add(dueDateCol);
		scheduledTransactionTable.sort();
	}
	
	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
