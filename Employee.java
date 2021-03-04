/**
 * This class defines the abstract data type Employee which encapsulates the
 * profile data along with the payment information for an employee.
 *
 * @author Siddhi Kasera, Sonal Madhok
 */
public class Employee {
    private Profile profile;
    private double payment;

    /**
     * A two parameter Employee constructor that initializes an Employee object when
     * it is created.
     *
     * @param profile attribute for an employee object
     */
    public Employee(Profile profile) {
        this.profile = profile;
        this.payment = 0;
    }

    /**
     * Returns the profile instance variable for an employee
     *
     * @return profile attribute for an Employee Object.
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Empty method written in Employee in order to be overridden by subclasses
     */
    public void calculatePayment() {

    }

    /**
     * Returns the payment for an employee
     *
     * @return payment number for the employee as a double
     */
    public double getPayment() {
        return payment;
    }

    /**
     * Returns true if this employee profile is equal to the argument employee
     * profile.
     *
     * @param obj the other employee
     * @return {@code true} if this employee has the same profile as {@code obj};
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee employee = (Employee) obj;
            return employee.profile.equals(this.profile);
        }
        return false;
    }

    /**
     * Returns a string representation of an employee
     *
     * @return a string representation of an employee.
     */
    public String toString() {

        return profile.toString();
    }
}
