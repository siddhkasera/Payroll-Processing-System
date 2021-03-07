package PayrollProcessingApp;

import java.text.DecimalFormat;

/**
 * This class defines the abstract data type sample.Fulltime which encapsulates the
 * data fields and methods for an employee.
 *
 * @author Siddhi Kasera, Sonal Madhok
 */
public class Fulltime extends Employee {

    private static final int payPeriod = 26;
    private double payment;
    private final double annualSalary;

    /**
     * A two parameter constructor that initializes a fulltime object for an employee
     * when created.
     *
     * @param profile   attribute for sample.Fulltime employee
     * @param annualSalary  attribute for sample.Fulltime employee
     */
    public Fulltime(Profile profile, double annualSalary) {
        super(profile);
        this.annualSalary = annualSalary;
        payment = 0;
    }

    /**
     * Returns the payment for a sample.Fulltime employee
     *
     * @return payment number for the employee as a double
     */
    public double getPayment() {
        return payment;
    }

    /**
     * Calculates payment for a fulltime employee
     */
    @Override
    public void calculatePayment() {
        payment = payment + (annualSalary / payPeriod);
    }

    /**
     * Returns true if this employee profile is equal to the argument employee
     * profile.
     *
     * @param obj the other employee
     * @return {@code true} if this fulltime employee has the same profile as the
     *         {@code obj}; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Fulltime) {
            Fulltime fulltime = (Fulltime) obj;
            return fulltime.getProfile().equals(this.getProfile());
        }
        return false;
    }

    /**
     * Returns the string representation of full time.
     *
     * @return returns the string representation for fulltime.
     */
    @Override
    public String toString() {
        String pattern = "###,##0.00";
        DecimalFormat df = new DecimalFormat(pattern);
        return String.format(super.toString() + "::Payment $" + df.format(payment) + "::FULL TIME::Annual Salary $" + df.format(annualSalary));
    }
}
