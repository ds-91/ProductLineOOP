package production;

public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public String code;

  ItemType(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }

  @Override
  public String toString() {
    return this.code;
  }
}
