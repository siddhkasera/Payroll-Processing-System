/**
 * This class defines the abstract data type Management which encapsulates the
 * data fields and methods for an employee.
 *
 * @author Siddhi Kasera, Sonal Madhok
 */
public class Management extends Fulltime {

    private static final int MANAGER = 1;
    private static final int DEPT_HEAD = 2;
    private static final int DIRECTOR = 3;
    private String roleStr;
    private double additionalComp;
    private double payment;

    /**
     * This is a three parameter constructor that initializes a Management object when
     * it is created.
     *
     * @param profile      attribute for a management object for an employee
     * @param annualSalary attribute for a management object for an employee
     * @param role         attribute for a management object for an employee
     */
    public Management(Profile profile, double annualSalary, int role) {
        super(profile, annualSalary);
        if (role == MANAGER) {
            additionalComp = 192.31;
            roleStr = "Manager";
        } else if (role == DEPT_HEAD) {
            additionalComp = 365.38;
            roleStr = "Department Head";
        } else if (role == DIRECTOR) {
            additionalComp = 461.54;
            roleStr = "Director";
        }
    }

    /**
     * Calculates payment for an employee with Management role.
     */
    public void calculatePayment() {
        super.calculatePayment();
        payment = super.getPayment();
        payment = payment + additionalComp;
    }

    /**
     * Compares name for an employee with management role.
     *
     * @param obj the other Management object
     * @return {@code true} if this management role employee has the same name as
     *         {@code obj} {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Management) {
            Management management = (Management) obj;
            return management.getProfile().getName().equals(this.getProfile().getName());
        }
        return false;
    }

    /**
     * Returns a string representation of compensation for a management role.
     *
     * @return a string representation of compensation for a management role.
     */
    @Override
    public String toString() {
        return super.toString() + "::" + roleStr + " Compensation $" + additionalComp;
    }
}
