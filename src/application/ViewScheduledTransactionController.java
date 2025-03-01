package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
	@FXML
	private TextField keywordTextField;
	private ScheduleTransactionBean selectedScheduledTransaction;
	ObservableList<ScheduleTransactionBean> scheduleTransactionList;

	public void initialize(URL url, ResourceBundle bundle) {
		scheduleTransactionList = FXCollections.observableArrayList();

		scheduleNameCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("scheduleName"));
		accountCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("account"));
		transactionTypeCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("transactionType"));
		frequencyCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("frequency"));
		dueDateCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("dueDate"));
		paymentAmountCol.setCellValueFactory(new PropertyValueFactory<ScheduleTransactionBean, String>("paymentAmount"));

		scheduleTransactionList = loadScheduledTransactionsInfo(); //change to load scheduledTransactionData();
		scheduledTransactionTable.setItems(scheduleTransactionList);
		
		//this could've been avoided if I set due date to a Integer instead of a string AUUUGHHH
		dueDateCol.setComparator((s1, s2) -> {
            Integer num1 = Integer.parseInt(s1);
            Integer num2 = Integer.parseInt(s2);
            return num1.compareTo(num2);
        });
		
		dueDateCol.setSortType(TableColumn.SortType.ASCENDING);
		scheduledTransactionTable.getSortOrder().add(dueDateCol);
		scheduledTransactionTable.sort();

		//implements search functionality
		FilteredList<ScheduleTransactionBean> filteredData = new FilteredList<>(scheduleTransactionList, b -> true);
		keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ScheduleTransactionBean -> {
				if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}

				String searchKeyWord = newValue.toLowerCase();
				if(ScheduleTransactionBean.getScheduleName().toLowerCase().indexOf(searchKeyWord) > -1) {
					return true;
				}
				return false;
			});
		});

		SortedList<ScheduleTransactionBean> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(scheduledTransactionTable.comparatorProperty());

		scheduledTransactionTable.setItems(sortedData);
		
		//check for click event
		scheduledTransactionTable.setOnMouseClicked((MouseEvent event) -> {
			if(event.getClickCount() >= 1) {
				selectedScheduledTransaction = getScheduledTransaction();
			}
		});
			
	}

	public void switchToHomeScene(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public ScheduleTransactionBean getScheduledTransaction() {
		ScheduleTransactionBean selectedScheduledTransaction = null;
		if(scheduledTransactionTable.getSelectionModel().getSelectedItem() != null) {
			selectedScheduledTransaction = scheduledTransactionTable.getSelectionModel().getSelectedItem();
		}
		
		return selectedScheduledTransaction;
	}
	
	public void switchToEditScene(ActionEvent event) throws IOException {
		if(selectedScheduledTransaction == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Alert!");
			alert.setContentText("Please select a scheduled transaction to edit!");
			Optional<ButtonType> result = alert.showAndWait();
			return;
		}
		
		//add controller logic here
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditScheduleTransactionScene.fxml"));
		Parent root = loader.load(); 
		EditScheduledTransactionController controller = loader.getController();
		controller.setScheduledTransaction(selectedScheduledTransaction, scheduleTransactionList);
		
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
