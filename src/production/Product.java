package production;

import java.util.Collection;

/**
 * Abstract class that implements {@link production.Item} and represents a Product that can be
 * produced. All items produced are types Product.
 *
 * @author Derek Schweizer
 */
public abstract class Product implements Item {

  private int id;
  private String type;
  private String manufacturer;
  private String name;

  /**
   * Constructor for Product that sets all instance fields.
   *
   * @param type The type of the product.
   * @param manufacturer The manufacturer of the product.
   * @param name The name of the product.
   */
  public Product(String type, String manufacturer, String name) {
    this.type = type;
    this.manufacturer = manufacturer;
    this.name = name;
  }

  /** Default no-argument constructor. */
  /* CheckStyle states for the right } to be alone on a line, but using the Google formatter,
   * it puts the two braces together.
   */
  public Product() {}

  /**
   * Gets the ID of this Product.
   *
   * @return this Product's ID.
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * Sets the name of this Product.
   *
   * @param name The name of the product.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the name of this Product.
   *
   * @return this Product's name.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Sets the manufacturer of this Product.
   *
   * @param manufacturer The name of the manufacturer.
   */
  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Gets this Product's manufacturer name.
   *
   * @return this Product's manufacturer.
   */
  @Override
  public String getManufacturer() {
    return this.manufacturer;
  }

  /**
   * Sets the ID of this Product.
   *
   * @param id the ID of this Product.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the type of this Product.
   *
   * @return this Product's type.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets this Product's type.
   *
   * @param type the type of the Product.
   */
  public void setType(String type) {
    this.type = type;
  }

  // Additional Challenge
  public static <T> void printType(Collection<Product> collection, Class<T> type) {
    for (Product p : collection) {
      if (type.isInstance(p)) {
        System.out.println(p.getName() + " is a type of " + type);
      }
    }
  }

  /**
   * Method that concatenates a string of this Product's instance fields.
   *
   * @return String of concatenated instance fields.
   */
  @Override
  public String toString() {
    return "Name: " + this.name + "\nManufacturer: " + this.manufacturer + "\nType: " + this.type;
  }
}
