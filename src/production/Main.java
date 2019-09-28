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
   * @param primaryStage The primary stage of the JavaFX GUI that will be shown.
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(root, 300, 275));
    primaryStage.show();
  }

  /** @param args Program arguments that can be passed in on startup. */
  public static void main(String[] args) {
    launch(args);
  }
}
