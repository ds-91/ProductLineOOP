package production;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the JavaFX GUI 'start' method and displays the GUI.
 *
 * @author Derek Scwheizer
 */
public class Main extends Application {

  /**
   * Called when the application is started. Starts the display of the user interface.
   *
   * @param primaryStage The primary stage of the JavaFX GUI that will be shown.
   * @throws Exception Exception when the FXMLLoader cannot load the current class.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production Line");
    primaryStage.setScene(new Scene(root, 500, 340));
    primaryStage.show();
  }

  /**
   * Called when the application is first ran. Launches the start method to display the UI.
   *
   * @param args Program arguments that can be passed in on startup.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
