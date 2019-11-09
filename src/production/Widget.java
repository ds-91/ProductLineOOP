package production;

/**
 * Represents a new {@link production.Product} object.
 *
 * @author Derek Schweizer
 */
public class Widget extends Product {

  /**
   * Constructor that calls the superclass {@link production.Product}'s constructor.
   *
   * @param type The product type.
   * @param manufacturer The product manufacturer.
   * @param name The product name.
   */
  public Widget(String type, String manufacturer, String name) {
    super(type, manufacturer, name);
  }
}
