package production;

import java.sql.Timestamp;
import java.util.Date;

public class ProductionRecord {

  private int productionNumber;
  private int productId;
  private String serialNumber;
  private Timestamp dateProduced;

  public ProductionRecord(Product p, int count) {
    super();
    this.serialNumber = p.getManufacturer().substring(0, 3) + p.getName().substring(0, 2) + String.format("%05d" , count);
    this.dateProduced = new Timestamp(new Date().getTime());
  }

  public ProductionRecord(int productId) {
    super();
    this.productId = productId;
    this.dateProduced = new Timestamp(new Date().getTime());
  }

  public ProductionRecord(int productionNumber, int productId, String serialNumber, Timestamp dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  public int getProductionNum() {
    return productionNumber;
  }

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productId;
  }

  public void setProductID(int productId) {
    this.productId = productId;
  }

  public String getSerialNum() {
    return serialNumber;
  }

  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Timestamp getProdDate() {
    return dateProduced;
  }

  public void setProdDate(Timestamp dateProduced) {
    this.dateProduced = dateProduced;
  }

  @Override
  public String toString() {
    return "Prod. Num: " + this.productionNumber + " Product ID: " + this.productId + " Serial Num: "
        + this.serialNumber + " Date: " + this.dateProduced;
  }
}
