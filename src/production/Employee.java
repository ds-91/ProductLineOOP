package production;

import java.util.Objects;

/**
 * Class that represents an Employee object that implements Comparable in order to use the .equals()
 * method.
 */
class Employee implements Comparable<Employee> {

  private final StringBuilder name;
  private String username;
  private final String password;
  private String email;

  /**
   * Constructor of Employee that checks if the input name contains a space and if it does set the
   * user email and username. Also checks if the password is valid and sets the employee password if
   * so.
   *
   * @param name The name of the employee.
   * @param password The password of the employee.
   */
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

  /**
   * Sets this employee's username to their first initial and their last name together.
   *
   * @param name This employee's name.
   */
  private void setUsername(String name) {
    String[] split = name.split("\\s+");
    this.username = String.valueOf(name.charAt(0)).toLowerCase() + split[1].toLowerCase();
  }

  /**
   * Checks if this employee's name contains a space.
   *
   * @param name This employees name.
   * @return A boolean if the name contains a space or not.
   */
  private boolean checkName(String name) {
    return name.contains(" ");
  }

  /**
   * Gets this employee's name.
   *
   * @return A StringBuilder of this employee's name.
   */
  private StringBuilder getName() {
    return this.name;
  }

  /**
   * Sets this employee's email to their firstname . lastname.
   *
   * @param name This employee's name.
   */
  private void setEmail(String name) {
    String[] split = name.split("\\s+");
    this.email = split[0].toLowerCase() + "." + split[1].toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * Checks if the password input is valid.
   *
   * @param password The password input
   * @return A boolean if the password is valid.
   */
  private boolean isValidPassword(String password) {
    return password.matches("^(?=.{3,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*!=]).*$");
  }

  /**
   * Method that overrides the toString() method to return a string of this employee's fields.
   *
   * @return A String of this employee's instance fields.
   */
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

  /**
   * Method that overrides compareTo() in order to compare two employees by their name.
   *
   * @param emp The employee to compare this employee to.
   * @return An integer indicating the comparison results.
   */
  @Override
  public int compareTo(Employee emp) {
    if (this.getName().toString().compareTo(emp.getName().toString()) == 0) {
      return 0;
    }
    return 1;
  }

  /**
   * Method required by the Comparable interface. Generates a hash code for each input value.
   *
   * @return The hash code generated.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, username, password, email);
  }

  /**
   * Method required by the Comparable interface. Checks if the parameter object is equal to this
   * employee's superclass.
   *
   * @param obj The object to compare this employee to.
   * @return A boolean if the two classes are equal or not.
   */
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
