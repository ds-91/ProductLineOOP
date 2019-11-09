package production;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Represents a record when a {@link production.Product} is produced. A {@link production.Product}
 * gets a new record every time it is produced.
 *
 * @author Derek Schweizer
 */
public class ProductionRecord {
  private int productionNumber;
  private int productId;
  private String serialNumber;
  private Timestamp dateProduced;

  /**
   * Constructor that sets the serial number and date produced for the record.
   *
   * @param p The product that is being produced.
   * @param count The number of products of type to produce.
   */
  public ProductionRecord(Product p, int count) {
    super();
    this.serialNumber =
        p.getManufacturer().substring(0, 3)
            + p.getName().substring(0, 2)
            + String.format("%05d", count);
    this.dateProduced = new Timestamp(new Date().getTime());
  }

  /**
   * Constructor that takes a productId and creates a record.
   *
   * @param productId the Id of the product to be recorded.
   */
  public ProductionRecord(int productId) {
    super();
    this.productId = productId;
    this.dateProduced = new Timestamp(new Date().getTime());
  }

  /**
   * Constructor that sets all instance fields with the parameters.
   *
   * @param productionNumber The number of the record in the order it was produced.
   * @param productId The product id of the {@link production.Product}.
   * @param serialNumber The serial number of the {@link production.Product}
   * @param dateProduced The date that the {@link production.Product} was produced.
   */
  public ProductionRecord(
      int productionNumber, int productId, String serialNumber, Timestamp dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = new Timestamp(dateProduced.getTime());
  }

  /**
   * Gets this production record's number.
   *
   * @return this production record's number.
   */
  public int getProductionNum() {
    return productionNumber;
  }

  /**
   * Sets the production number for this record.
   *
   * @param productionNumber the production number.
   */
  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Gets this production record's product id.
   *
   * @return this production record's product id.
   */
  public int getProductID() {
    return productId;
  }

  /**
   * Sets this production record's product id.
   *
   * @param productId the product id.
   */
  public void setProductID(int productId) {
    this.productId = productId;
  }

  /**
   * Gets this production record's serial number.
   *
   * @return this production record's serial number.
   */
  public String getSerialNum() {
    return serialNumber;
  }

  /**
   * Sets this production record's serial number.
   *
   * @param serialNumber the product's serial number.
   */
  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Gets this production record's production date.
   *
   * @return this production record's production date.
   */
  public Timestamp getProdDate() {
    return new Timestamp(this.dateProduced.getTime());
  }

  /**
   * Sets this production record's production date.
   *
   * @param dateProduced the production date.
   */
  public void setProdDate(Timestamp dateProduced) {
    this.dateProduced = new Timestamp(dateProduced.getTime());
  }

  /**
   * Gets a string of this production record's instance fields.
   *
   * @return this production record's instance fields.
   */
  @Override
  public String toString() {
    return "Prod. Num: "
        + this.productionNumber
        + " Product ID: "
        + this.productId
        + " Serial Num: "
        + this.serialNumber
        + " Date: "
        + this.dateProduced;
  }
}
