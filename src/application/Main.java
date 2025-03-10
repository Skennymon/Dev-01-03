package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application implements DataAccessLayer {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//sets up and initializes home page when the user launches the app
			Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			checkForDuedSchedules();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//confirmed we're good
	//11/16/2024 fixed bug
	//11/21/2024
	//pain
	//I am free man 12/1/2024
}
