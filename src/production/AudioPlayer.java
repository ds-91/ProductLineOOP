package production;

/**
 * AudioPlayer class that extends {@link production.Product} and implements {@link
 * production.MultimediaControl}. Represents a Product that is a type AudioPlayer.
 *
 * @author Derek Schweizer
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /**
   * AudioPlayer constructor that calls the Product constructor and sets fields from parameters.
   *
   * @param name The name of the audio player.
   * @param manufacturer The manufacturer of the audio player.
   * @param supportedAudioFormats The audio formats this audio player supports.
   * @param supportedPlaylistFormats The playlist formats this audio player supports.
   */
  public AudioPlayer(
      String name,
      String manufacturer,
      String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super();
    super.setType("AUDIO");
    super.setName(name);
    super.setManufacturer(manufacturer);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * AudioPlayer constructor that called the Product constructor.
   *
   * @param type The type of the audio player.
   * @param manufacturer The manufacturer of the audio player.
   * @param name The name of the audio player.
   */
  public AudioPlayer(String type, String manufacturer, String name) {
    super(type, manufacturer, name);
  }

  /** Instance method when the audio player is played. */
  @Override
  public void play() {
    System.out.println("Playing");
  }

  /** Instance method when the audio player is stopped. */
  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  /** Instance method when the audio player goes to the previous audio file. */
  @Override
  public void previous() {
    System.out.println("Previous");
  }

  /** Instance method when the audio player goes to the next audio file. */
  @Override
  public void next() {
    System.out.println("Next");
  }

  /**
   * Instance method that calls {@link production.Product} toString method and appends audio player
   * instance variables to it.
   *
   * @return A concatenated string of Product and AudioPlayer fields.
   */
  @Override
  public String toString() {
    return super.toString()
        + "\nSupported Audio Formats: "
        + this.supportedAudioFormats
        + "\nSupported Playlist Formats: "
        + this.supportedPlaylistFormats;
  }
}
