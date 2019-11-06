package production;

class AudioPlayerDriver {
  public static void main(String[] args) {
    AudioPlayer myplayer = new AudioPlayer("iPod", "Apple", ".mp4", "all");
    System.out.println(myplayer.toString());
  }
}
