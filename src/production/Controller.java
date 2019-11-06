package production;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class that handles the logic for the GUI to Database relationship.
 *
 * @author Derek Schweizer
 */
public class Controller {

  private static final String jdbcDriver = "org.h2.Driver";
  private static final String dbUrl = "jdbc:h2:./res/ProductionDB";

  // Empty database password for now (FindBugs)
  private static final String user = "";
  private static final String pass = "";

  @FXML private TextField txtProductName;
  @FXML private TextField txtManufacturer;
  @FXML private ChoiceBox choiceItemType;
  @FXML private ComboBox comboItemQuantity;
  @FXML private TextArea txtProductionLog;
  @FXML private TableView tableExistingProducts;
  @FXML private ListView listAllProducts;
  @FXML private TableColumn productNameColumn;
  @FXML private TableColumn productTypeColumn;

  private ObservableList<Product> productLine;

  /** Initializes any GUI fields or parameters when the GUI is launched. */
  public void initialize() {
    ObservableList<Integer> quantityOptions =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    comboItemQuantity.setItems(quantityOptions);
    comboItemQuantity.setEditable(true);
    comboItemQuantity.getSelectionModel().selectFirst();

    ObservableList<ItemType> itemTypeList = FXCollections.observableArrayList();

    for (ItemType itemType : ItemType.values()) {
      //itemTypeList.add(itemType);
      itemTypeList.addAll(itemType);
    }
    choiceItemType.setItems(itemTypeList);


    /** Issue 4 "Display the production record in the text area" - Not sure what is meant */
    ProductionRecord pr = new ProductionRecord(0, 1, "00000", new Date());
    txtProductionLog.appendText(pr.toString() + "\n");

    testMultimedia();
    setupProductLineTable();
    setupProductListView();
  }

  /**
   * Adds user inputted data into the Product database.
   *
   * <p>When the users clicks the Add button this method will collect the required data (item name,
   * manufacturer, item type) and create an SQL statement to execute that adds that item to the
   * database.
   *
   * @param actionEvent The action of when the button is clicked.
   */
  public void actionAddButton(ActionEvent actionEvent) {
    if (txtProductName.getLength() > 0 && txtManufacturer.getLength() > 0) {
      Widget newProduct =
          new Widget(
              choiceItemType.getSelectionModel().getSelectedItem().toString(),
              txtManufacturer.getText(),
              txtProductName.getText());
      try {
        Class.forName(jdbcDriver);
        Connection conn = DriverManager.getConnection(dbUrl, user, pass);
        PreparedStatement stmt =
            conn.prepareStatement("INSERT INTO Product(type, manufacturer, name) VALUES(?, ?, ?)");
        stmt.setString(1, newProduct.getType());
        stmt.setString(2, newProduct.getManufacturer());
        stmt.setString(3, newProduct.getName());

        stmt.execute();

        productLine.add(newProduct);
        setupProductListView();

        // Closes the prepared statement and connection (FindBugs)
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setContentText("Product name and Manufacturer must be filled in!");
      alert.show();
    }
  }

  /**
   * Method that records the completed production and quantity of a selected item.
   *
   * @param actionEvent The action of when the button is clicked.
   */
  public void actionRecordProduction(ActionEvent actionEvent) {
    System.out.println("record production pressed");
  }

  private static void testMultimedia() {
    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }

  private void setupProductLineTable() {

    productLine = FXCollections.observableArrayList();

    productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
    productTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));

    tableExistingProducts.setItems(productLine);
  }

  private void setupProductListView() {
    listAllProducts.setItems(productLine);
  }
}
