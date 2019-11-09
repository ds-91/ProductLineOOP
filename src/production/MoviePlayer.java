package production;

/**
 * MoviePlayer class that extends {@link production.Product} and implements {@link
 * production.MultimediaControl}. Represents a Movie Player object.
 *
 * @author Derek Schweizer
 */
public class MoviePlayer extends Product implements MultimediaControl {

  private Screen screen;
  private MonitorType monitorType;

  /**
   * Movie Player constructor.
   *
   * @param name The name of the movie player.
   * @param manufacturer The manufacturer of the movie player.
   * @param screen The {@link production.Screen} the movie player has.
   * @param monitorType The {@link production.MonitorType} the movie player has.
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    this.setType("VISUAL");
    this.setName(name);
    this.setManufacturer(manufacturer);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /** Instance method when the movie player is played. */
  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  /** Instance method when the movie player is stopped. */
  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  /** Instance method when the movie player goes to the previous movie. */
  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  /** Instance method when the movie player goes to the next movie. */
  @Override
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * Returns a string of this object's fields.
   *
   * @return Returns a concatenated string containing {@link production.Product} fields and
   *     MoviePlayer instance fields.
   */
  @Override
  public String toString() {
    return super.toString() + "\n" + this.screen.toString() + "\nMonitor Type: " + this.monitorType;
  }
}
