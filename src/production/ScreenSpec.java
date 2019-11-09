package production;

/**
 * Interface that {@link production.Screen} implements to get specifications about the Screen.
 *
 * @author Derek Schweizer
 */
public interface ScreenSpec {

  /**
   * Gets the screen resolution.
   *
   * @return screen resolution.
   */
  String getResolution();

  /**
   * Gets the screen refresh rate.
   *
   * @return screen refresh rate.
   */
  int getRefreshRate();

  /**
   * Gets the screen response time.
   *
   * @return screen response time.
   */
  int getResponseTime();
}
