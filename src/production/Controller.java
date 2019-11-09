package production;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
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

  private ObservableList<Product> productLine = FXCollections.observableArrayList();
  private ArrayList<ProductionRecord> productionLog = new ArrayList();

  /** Initializes any GUI fields or parameters when the GUI is launched. */
  public void initialize() {
    ObservableList<Integer> quantityOptions =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    comboItemQuantity.setItems(quantityOptions);
    comboItemQuantity.setEditable(true);
    comboItemQuantity.getSelectionModel().selectFirst();

    ObservableList<ItemType> itemTypeList = FXCollections.observableArrayList();

    for (ItemType itemType : ItemType.values()) {
      itemTypeList.add(itemType);
    }

    choiceItemType.setItems(itemTypeList);

    setupProductLineTable();
    loadProductList();
    loadProductionLog();

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
        // Blank username and password for now (FindBugs)
        Connection conn = DriverManager.getConnection(dbUrl, user, pass);
        PreparedStatement stmt =
            conn.prepareStatement("INSERT INTO Product(type, manufacturer, name) VALUES(?, ?, ?)");
        stmt.setString(1, newProduct.getType());
        stmt.setString(2, newProduct.getManufacturer());
        stmt.setString(3, newProduct.getName());

        stmt.execute();

        productLine.add(newProduct);

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
    setupProductListView();
    loadProductList();
  }

  /**
   * Method that records the completed production and quantity of a selected item.
   *
   * @param actionEvent The action of when the button is clicked.
   */
  public void actionRecordProduction(ActionEvent actionEvent) {
    String selectedItem = listAllProducts.getSelectionModel().getSelectedItem().toString();
    int quantity = Integer.parseInt(comboItemQuantity.getSelectionModel().getSelectedItem().toString());
    String[] itemLines = selectedItem.split("\\r?\\n");

    String selectedItemName = itemLines[0].substring(itemLines[0].indexOf(" ") + 1);
    String selectedItemManufacturer = itemLines[1].substring(itemLines[1].indexOf(" ") + 1);
    String selectedItemType = itemLines[2].substring(itemLines[2].indexOf(" ") + 1);

    Product p = new Widget(selectedItemType, selectedItemManufacturer, selectedItemName);
    ProductionRecord pr = new ProductionRecord(p, 0);

    ArrayList<ProductionRecord> productionRun = new ArrayList();
    for (int i = 0; i < quantity; i++) {
      productionRun.add(pr);
    }
    addToProductionDB(productionRun);

    loadProductionLog();
    showProduction();
  }

  public void addToProductionDB(ArrayList<ProductionRecord> productionRun) {
    for (ProductionRecord pr : productionRun) {
      try {
        Class.forName(jdbcDriver);
        // Blank username and password for now (FindBugs)
        Connection conn = DriverManager.getConnection(dbUrl, user, pass);
        PreparedStatement stmt =
            conn.prepareStatement(
                "INSERT INTO ProductionRecord(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES(?, ?, ?)");
        stmt.setInt(1, pr.getProductID());
        stmt.setString(2, pr.getSerialNum());
        stmt.setTimestamp(3, pr.getProdDate());

        stmt.execute();

        // Closes the prepared statement and connection (FindBugs)
        stmt.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void setupProductLineTable() {

    productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
    productTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));

    tableExistingProducts.setItems(productLine);
  }

  private void setupProductListView() {
    listAllProducts.setItems(productLine);
  }

  private void loadProductList() {
    try {
      Class.forName(jdbcDriver);
      // Blank username and password for now (FindBugs)
      Connection conn = DriverManager.getConnection(dbUrl, user, pass);
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCT");

      ResultSet rs = stmt.executeQuery();

      Product databaseProduct;

      while (rs.next()) {
        String productName = rs.getString(2);
        String productType = rs.getString(3);
        String productManufacturer = rs.getString(4);

        databaseProduct = new Widget(productType, productManufacturer, productName);
        productLine.add(databaseProduct);
      }

      // Closes the prepared statement and connection (FindBugs)
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public void loadProductionLog() {
    if (productionLog.size() > 0) {
      productionLog.clear();
    }
    try {
      Class.forName(jdbcDriver);
      // Blank username and password for now (FindBugs)
      Connection conn = DriverManager.getConnection(dbUrl, user, pass);
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTIONRECORD");

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        productionLog.add(
            new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getTimestamp(4)));
      }

      // Closes the prepared statement and connection (FindBugs)
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    showProduction();
  }

  public void showProduction() {
    txtProductionLog.clear();
    for (ProductionRecord pr : productionLog) {
      txtProductionLog.appendText(
          "Prod. Num: "
              + pr.getProductionNum()
              + " Product ID: "
              + pr.getProductID()
              + " Serial Num: "
              + pr.getSerialNum()
              + " Date: "
              + pr.getProdDate()
              + "\n");
    }
  }
}
