package production;

public class Employee {

  private StringBuilder name;
  private String username;
  private String password;
  private String email;

  public Employee(String name, String password) {
    this.name = new StringBuilder().append(name);
    if (name.contains(" ")) {
      setUsername(name);
      setEmail(name);
    } else {
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }

    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  public void setUsername(String name) {
    String[] split = name.split("\\s+");

    this.username = String.valueOf(name.charAt(0)).toLowerCase() + split[1].toLowerCase();
  }

  public boolean checkName(String name) {

    return false;
  }

  public void setEmail(String name)  {
    String[] split = name.split("\\s+");
    this.email = split[0].toLowerCase() + "." + split[1].toLowerCase() + "@oracleacademy.Test";
  }

  public boolean isValidPassword(String password) {
    if (password.matches("^(?=.{3,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*!=]).*$")) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return "Employee Details\nName : " + this.name + "\n" +
        "Username : " + this.username + "\n" +
        "Email : " + this.email + "\n" +
        "Initial Password : " + this.password;
  }
}
