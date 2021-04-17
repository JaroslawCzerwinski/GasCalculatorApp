package eng.gascalculator.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/eng/gascalculator/view/MainPane.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("Gas calculator v1.1");
		stage.show();

	}

	public static void main(String [] args){
		launch(args);
	}

}
