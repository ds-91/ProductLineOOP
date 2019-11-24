package production;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
  @FXML private ChoiceBox<ItemType> choiceItemType;
  @FXML private ComboBox comboItemQuantity;
  @FXML private TextArea txtProductionLog;
  @FXML private TableView<Product> tableExistingProducts;
  @FXML private ListView<Product> listAllProducts;
  @FXML private TableColumn<?, ?> productNameColumn;
  @FXML private TableColumn<?, ?> productTypeColumn;
  @FXML private Button btnAddProduct;
  @FXML private Button btnRecordProduction;

  private final ObservableList<Product> productLine = FXCollections.observableArrayList();
  private final ArrayList<ProductionRecord> productionLog = new ArrayList<>();

  /** Initializes any GUI fields or parameters when the GUI is launched. */
  public void initialize() {
    ObservableList<Integer> quantityOptions =
        FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    comboItemQuantity.setItems(quantityOptions);
    comboItemQuantity.setEditable(true);
    comboItemQuantity.getSelectionModel().selectFirst();

    ObservableList<ItemType> itemTypeList = FXCollections.observableArrayList();

    itemTypeList.addAll(ItemType.values());

    choiceItemType.setItems(itemTypeList);

    setupProductLineTable();
    loadProductList();
    loadProductionLog();
    setupListViewPlaceholder();

    setupProductListView();
  }

  /**
   * Method called in the initialize method that sets the placeholder text of the two ListViews.
   */
  public void setupListViewPlaceholder() {
    tableExistingProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    listAllProducts.setPlaceholder(new Label("No content"));
    tableExistingProducts.setPlaceholder(new Label("No content"));
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
    /*
     * Collects the data from the GUI ListView of all products able to be produced. Takes each line
     * and parses it to create a new ProductionRecord object and add it to the production log.
     */
    String selectedItem = listAllProducts.getSelectionModel().getSelectedItem().toString();
    int quantity =
        Integer.parseInt(comboItemQuantity.getSelectionModel().getSelectedItem().toString());
    String[] itemLines = selectedItem.split("\\r?\\n");

    String selectedItemName = itemLines[0].substring(itemLines[0].indexOf(" ") + 1);
    String selectedItemManufacturer = itemLines[1].substring(itemLines[1].indexOf(" ") + 1);
    String selectedItemType = itemLines[2].substring(itemLines[2].indexOf(" ") + 1);

    Product p = new Widget(selectedItemType, selectedItemManufacturer, selectedItemName);
    p.setId(listAllProducts.getSelectionModel().getSelectedIndex());
    ProductionRecord pr = new ProductionRecord(p, quantity);

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();
    for (int i = 0; i < quantity; i++) {
      productionRun.add(pr);
    }
    addToProductionDB(productionRun);

    loadProductionLog();
    showProduction();
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
        // Blank username and password for now (FindBugs)
        Connection conn = DriverManager.getConnection(dbUrl, user, pass);
        PreparedStatement stmt =
            conn.prepareStatement(
                "INSERT INTO ProductionRecord(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) "
                    + "VALUES(?, ?, ?)");
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

  /**
   * Method called during initialization. This method queries the ProductionRecord table and creates
   * a new ProductionRecord object and adds it to the productionLog ArrayList.
   */
  private void loadProductionLog() {
    /*
     * Clear the productionLog ArrayList if it contains any elements. This is because this method is
     * called more than once and results in the ArrayList having all elements duplicated.
     */
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

  /**
   * Method called during initialization. This method looks through the productionLog ArrayList and
   * appends the object fields into the production log TextArea. TextField cleared when the method
   * is called as to not repeat entries.
   */
  private void showProduction() {
    txtProductionLog.clear();
    for (ProductionRecord pr : productionLog) {
      String itemName = productLine.get(pr.getProductID()).getName();
      txtProductionLog.appendText(
          "Prod. Num: "
              + pr.getProductionNum()
              + " Product Name: "
              + itemName
              + " Serial Num: "
              + pr.getSerialNum()
              + " Date: "
              + pr.getProdDate()
              + "\n");
    }
  }
}
