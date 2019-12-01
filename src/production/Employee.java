package production;

import java.util.Objects;

class Employee implements Comparable<Employee> {

  private final StringBuilder name;
  private String username;
  private final String password;
  private String email;

  public Employee(String name, String password) {
    this.name = new StringBuilder().append(name);
    if (checkName(name)) {
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

  private void setUsername(String name) {
    String[] split = name.split("\\s+");
    this.username = String.valueOf(name.charAt(0)).toLowerCase() + split[1].toLowerCase();
  }

  private boolean checkName(String name) {
    return name.contains(" ");
  }

  private StringBuilder getName() {
    return this.name;
  }

  private void setEmail(String name) {
    String[] split = name.split("\\s+");
    this.email = split[0].toLowerCase() + "." + split[1].toLowerCase() + "@oracleacademy.Test";
  }

  private boolean isValidPassword(String password) {
    return password.matches("^(?=.{3,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*!=]).*$");
  }

  @Override
  public String toString() {
    return "Employee Details\nName : "
        + this.name
        + "\n"
        + "Username : "
        + this.username
        + "\n"
        + "Email : "
        + this.email
        + "\n"
        + "Initial Password : "
        + this.password;
  }

  @Override
  public int compareTo(Employee emp) {
    if (this.getName().toString().compareTo(emp.getName().toString()) == 0) {
      return 0;
    }
    return 1;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, username, password, email);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
