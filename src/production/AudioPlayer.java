package production;

public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats) {
    super();
    super.setType("AUDIO");
    super.setName(name);
    super.setManufacturer(manufacturer);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  public AudioPlayer(String type, String manufacturer, String name) {
    super(type, manufacturer, name);
  }

  @Override
  public void play() {
    System.out.println("Playing");
  }

  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  @Override
  public void previous() {
    System.out.println("Previous");
  }

  @Override
  public void next() {
    System.out.println("Next");
  }

  @Override
  public String toString() {
    return super.toString() + "\nSupported Audio Formats: " + this.supportedAudioFormats +
        "\nSupported Playlist Formats: " + this.supportedPlaylistFormats;
  }
}
