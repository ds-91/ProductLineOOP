package production;

/**
 * Interface MultimediaControl that {@link production.AudioPlayer} and {@link
 * production.MoviePlayer} implement.
 *
 * @author Derek Schweizer
 */
public interface MultimediaControl {

  /** Method to play the media. */
  void play();

  /** Method to stop playing the media. */
  void stop();

  /** Method to go to the previous media. */
  void previous();

  /** Method to go to the next media. */
  void next();
}
