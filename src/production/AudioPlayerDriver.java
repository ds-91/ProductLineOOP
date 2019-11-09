package production;

/**
 * Driver class for the AudioPlayer class. Creates a new AudioPlayer object and displays its
 * toString method.
 *
 * @author Derek Schweizer
 */
class AudioPlayerDriver {

  /**
   * Main method that is ran when the class starts.
   *
   * @param args Arguments array that can be passed in from the command line.
   */
  public static void main(String[] args) {
    AudioPlayer myplayer = new AudioPlayer("iPod", "Apple", ".mp4", "all");
    System.out.println(myplayer.toString());
  }
}
