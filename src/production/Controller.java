package production;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

  private String user = "";
  private String pass = "";

  @FXML private TextField txtProductName;
  @FXML private TextField txtManufacturer;
  @FXML private TextField txtFullName;
  @FXML private TextField txtPassword;
  @FXML private ComboBox comboItemQuantity;
  @FXML private ChoiceBox<ItemType> choiceItemType;
  @FXML private ListView<Product> listAllProducts;
  @FXML private TableView<Product> tableExistingProducts;
  @FXML private TableColumn<?, ?> productNameColumn;
  @FXML private TableColumn<?, ?> productTypeColumn;
  @FXML private TableView<ProductionRecord> tableProductionLog;
  @FXML private TableColumn<ProductionRecord, Integer> recordProductionNumberColumn;
  @FXML private TableColumn<ProductionRecord, String> recordSerialNumberColumn;
  @FXML private TableColumn<ProductionRecord, Timestamp> recordDateProducedColumn;
  @FXML private Button btnAddProduct;
  @FXML private Button btnRecordProduction;
  @FXML private Button btnCreateEmployee;

  private final ObservableList<Product> productLine = FXCollections.observableArrayList();
  private final ObservableList<ProductionRecord> productionRecordLog =
      FXCollections.observableArrayList();

  /** Initializes any GUI fields or parameters when the GUI is launched. */
  public void initialize() {
    initializeDatabaseInfo();
    ObservableList<Integer> quantityOptions =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    comboItemQuantity.setItems(quantityOptions);
    comboItemQuantity.setEditable(true);
    comboItemQuantity.getSelectionModel().selectFirst();

    ObservableList<ItemType> itemTypeList = FXCollections.observableArrayList();

    itemTypeList.addAll(ItemType.values());

    choiceItemType.setItems(itemTypeList);

    setupProductLineTable();
    setupProductionLineTable();
    loadProductList();
    loadProductionLog();
    setupListViewPlaceholder();
    setupProductListView();
  }

  /** Called during initialization. Loads database credentials from properties file. */
  private void initializeDatabaseInfo() {
    try {
      Properties prop = new Properties();
      prop.load(new FileInputStream("res/properties"));
      pass = reverseDatabasePassword(prop.getProperty("password"));
      user = prop.getProperty("user");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reverses the database password from the properties file and returns it.
   *
   * @param pw The database password from the properties file that needs to be reversed.
   * @return A reversed string.
   */
  private String reverseDatabasePassword(String pw) {
    if (pw.length() <= 1) {
      return pw;
    }
    return pw.charAt(pw.length() - 1) + reverseDatabasePassword(pw.substring(0, pw.length() - 1));
  }

  /** Method called in the initialize method that sets the placeholder text of the two ListViews. */
  private void setupListViewPlaceholder() {
    tableExistingProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tableProductionLog.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    listAllProducts.setPlaceholder(new Label("No content"));
    tableExistingProducts.setPlaceholder(new Label("No content"));
    tableProductionLog.setPlaceholder(new Label("No content"));
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
    if (txtProductName.getLength() >= 3 && txtManufacturer.getLength() >= 3) {
      if (!choiceItemType.getSelectionModel().isEmpty()) {
        Widget newProduct =
            new Widget(
                choiceItemType.getSelectionModel().getSelectedItem().toString(),
                txtManufacturer.getText(),
                txtProductName.getText());
        try {
          Class.forName(jdbcDriver);
          Connection conn = DriverManager.getConnection(dbUrl, user, pass);
          PreparedStatement stmt =
              conn.prepareStatement(
                  "INSERT INTO Product(type, manufacturer, name) VALUES(?, ?, ?)");
          stmt.setString(1, newProduct.getType());
          stmt.setString(2, newProduct.getManufacturer());
          stmt.setString(3, newProduct.getName());

          stmt.execute();

          productLine.add(newProduct);

          stmt.close();
          conn.close();
        } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
      } else {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText("Item type must be selected!");
        a.show();
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
    String selectedItem = null;
    try {
      selectedItem = listAllProducts.getSelectionModel().getSelectedItem().toString();
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    if (comboItemQuantity.getSelectionModel().getSelectedItem().toString().matches("^[0-9]+$")) {
      if (selectedItem != null) {
        int quantity =
            Integer.parseInt(comboItemQuantity.getSelectionModel().getSelectedItem().toString());
        String[] itemLines = selectedItem.split("\\r?\\n");

        String selectedItemName = itemLines[0].substring(itemLines[0].indexOf(" ") + 1);
        String selectedItemManufacturer = itemLines[1].substring(itemLines[1].indexOf(" ") + 1);
        String selectedItemType = itemLines[2].substring(itemLines[2].indexOf(" ") + 1);

        Product p = new Widget(selectedItemType, selectedItemManufacturer, selectedItemName);
        p.setId(listAllProducts.getSelectionModel().getSelectedIndex());

        ArrayList<ProductionRecord> productionRun = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
          ProductionRecord pr = new ProductionRecord(p, i);
          productionRun.add(pr);
        }
        addToProductionDB(productionRun);

        loadProductionLog();
      } else {
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText("You must select an item to produce!");
        a.show();
      }
    } else {
      Alert a = new Alert(AlertType.ERROR);
      a.setContentText("Item quantity is invalid!");
      a.show();
    }
  }

  /**
   * Takes an ArrayList of specified type and loops through it and adds it as a row in the database.
   *
   * @param productionRun ArrayList of type ProductionRecord that is declared at class level used
   *     for storing ProductionRecord objects which will then be added to the database.
   */
  private void addToProductionDB(ArrayList<ProductionRecord> productionRun) {
    for (ProductionRecord pr : productionRun) {
      try {
        Class.forName(jdbcDriver);
        Connection conn = DriverManager.getConnection(dbUrl, user, pass);
        PreparedStatement stmt =
            conn.prepareStatement(
                "INSERT INTO ProductionRecord(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) "
                    + "VALUES(?, ?, ?)");
        stmt.setInt(1, pr.getProductId());
        stmt.setString(2, pr.getSerialNumber());
        stmt.setTimestamp(3, pr.getDateProduced());

        stmt.execute();

        stmt.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Method called during initialization. It registers two columns in the existing products ListView
   * displayed in the GUI and sets its contents to the class level ArrayList productLine.
   */
  private void setupProductLineTable() {
    productNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
    productTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));

    tableExistingProducts.setItems(productLine);
  }

  /**
   * Called during initialization. Registers {@link ProductionRecord} object properties to TableView
   * columns and adds all items from the list to the table.
   */
  private void setupProductionLineTable() {
    recordProductionNumberColumn.setCellValueFactory(new PropertyValueFactory("productionNumber"));
    recordSerialNumberColumn.setCellValueFactory(new PropertyValueFactory("serialNumber"));
    recordDateProducedColumn.setCellValueFactory(new PropertyValueFactory("dateProduced"));

    tableProductionLog.setItems(productionRecordLog);
  }

  /**
   * Method called during initialization. This method sets the contents of the record production
   * ListView.
   */
  private void setupProductListView() {
    listAllProducts.setItems(productLine);
  }

  /**
   * Method called during initialization. This method queries the Product database and for each row
   * it creates a new Product object and adds them to the class level ArrayList productLine.
   */
  private void loadProductList() {
    productLine.clear();
    try {
      Class.forName(jdbcDriver);
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

      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method called during initialization. This method queries the ProductionRecord table and creates
   * a new ProductionRecord object and adds it to the productionLog ObservableList.
   */
  private void loadProductionLog() {
    productionRecordLog.clear();
    try {
      Class.forName(jdbcDriver);
      Connection conn = DriverManager.getConnection(dbUrl, user, pass);
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTIONRECORD");

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        productionRecordLog.add(
            new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getTimestamp(4)));
      }

      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method called when the user clicks the 'Create Employee' button on the Employee tab. Displays
   * an Alert with the Employee's information when created and an error alert if the user entered
   * incorrect information.
   */
  public void actionCreateEmployee() {
    String fullName = txtFullName.getText();
    String password = txtPassword.getText();

    if (!fullName.isEmpty()) {
      if (fullName.matches("^[a-zA-Z]+\\s[a-zA-Z]+$")) {
        if (txtPassword.getLength() >= 6) {
          Employee emp = new Employee(fullName, password);
          Alert a = new Alert(AlertType.INFORMATION);
          a.setHeaderText("Employee creation");
          a.setContentText(emp.toString());
          a.show();

          txtFullName.clear();
          txtPassword.clear();
        } else {
          Alert a = new Alert(AlertType.WARNING);
          a.setHeaderText("Invalid information");
          a.setContentText("Password must be 6 characters or more!");
          a.show();
        }
      } else {
        Alert a = new Alert(AlertType.WARNING);
        a.setHeaderText("Invalid information");
        a.setContentText("Full name should be in the format of First name Last name!");
        a.show();
      }
    } else {
      Alert a = new Alert(AlertType.WARNING);
      a.setHeaderText("Invalid information");
      a.setContentText("You did not enter a name!");
      a.show();
    }
  }
}
