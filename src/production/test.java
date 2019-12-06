package production;

public class test {
  public static void main(String[] args) {
    String test = "Hello!";
    System.out.println(reverseString(test));
  }

  private static String reverseString(String id) {
    if (id.length() <= 1) return id;

    return id.charAt(id.length() - 1) + reverseString(id.substring(0, id.length() - 1));
  }
}
