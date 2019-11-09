package production;

/**
 * Represents a Screen on a {@link production.MoviePlayer}.
 *
 * @author Derek Schweizer
 */
public class Screen implements ScreenSpec {

  private String resolution;
  private int refreshrate;
  private int responsetime;

  /**
   * Constructor that sets all instance fields.
   *
   * @param resolution The screen resolution.
   * @param refreshrate The screen refresh rate.
   * @param responsetime The screen response time.
   */
  public Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  /**
   * Gets this screen's resolution.
   *
   * @return the screen resolution.
   */
  @Override
  public String getResolution() {
    return this.resolution;
  }

  /**
   * Gets this screen's refresh rate.
   *
   * @return the screen refresh rate.
   */
  @Override
  public int getRefreshRate() {
    return this.refreshrate;
  }

  /**
   * Gets this screen's response time.
   *
   * @return the screen response time.
   */
  @Override
  public int getResponseTime() {
    return this.responsetime;
  }

  /**
   * Returns a string of this Screen's fields.
   *
   * @return a string of instance fields.
   */
  @Override
  public String toString() {
    return "Screen:"
        + "\nResolution: "
        + this.resolution
        + "\nRefresh rate: "
        + this.refreshrate
        + "\nResponse time: "
        + this.responsetime;
  }
}
