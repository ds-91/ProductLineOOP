package production;

/**
 * Item interface that is implemented by {@link production.Product}.
 *
 * @author Derek Schweizer
 */
interface Item {

  /**
   * Returns the ID of the item.
   *
   * @return id of the item.
   */
  int getId();

  /**
   * Sets the item name.
   *
   * @param name the item name.
   */
  void setName(String name);

  /**
   * Gets the item name.
   *
   * @return the item name.
   */
  String getName();

  /**
   * Sets the item manufacturer.
   *
   * @param manufacturer the item manufacturer.
   */
  void setManufacturer(String manufacturer);

  /**
   * Gets the item manufacturer.
   *
   * @return the item manufacturer.
   */
  String getManufacturer();
}
