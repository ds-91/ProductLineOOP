package production;

/**
 * ItemType enumeration that holds what type of item the created {@link production.Product} is.
 *
 * @author Derek Schweizer
 */
enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public String code;

  /**
   * ItemType constructor that sets the ItemType string code.
   *
   * @param code String abbreviation for the ItemType.
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * Gets this ItemType's code.
   *
   * @return Returns the string code for the ItemType.
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Returns a string code for this ItemType.
   *
   * @return Returns the string code for the ItemType.
   */
  @Override
  public String toString() {
    return this.code;
  }
}
