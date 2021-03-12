package PayrollProcessing;

import java.io.PrintWriter;

/**
 * This class implements all the main methods.
 *
 * @author Siddhi Kasera, Sonal Madhok
 */
public class Company {
    private int numEmployee;
    private static final int CAPACITY = 4;
    private Employee[] emplist = new Employee[CAPACITY];
    private static final int NOT_FOUND = -1;
    private int empExists;
    private StringBuilder textOut = new StringBuilder();
    private String outStr = "";


    /**
     * Private method is looks for an employee in an array.
     *
     * @param employee object that has to be found from the array
     * @return return 1 if an employee is there in the array and 0 when the employee
     *         is not there
     */
    private int find(Employee employee) {
        int flag = 0;
        for (int i = 0; i < numEmployee; i++) {
            if (emplist[i].getProfile().equals(employee.getProfile())) {
                flag = 1;
                return flag;
            }
        }
        flag = 0;
        return flag;
    }

    /**
     * This method resizes the array by increasing the size by four everytime this
     * method called.
     */
    private void grow() {
        int oldLength = emplist.length;
        int newLength = oldLength + CAPACITY;
        Employee[] newEmployee = new Employee[newLength];
        for (int i = 0; i < oldLength; i++) {
            newEmployee[i] = emplist[i];
        }
        emplist = newEmployee;
    }

    /**
     * This method checks if an employee's profile already exists or not before they
     * are added to the array.
     *
     * @param employee object that has to be added in the array
     * @return boolean true if the employee has been added and false if it cannot add
     */
    public boolean add(Employee employee) {
        empExists = find(employee);
        if (empExists == 1) {
            return false;
        }
        if ((numEmployee + 1) <= emplist.length) {
            emplist[numEmployee] = employee;
        } else {
            this.grow();
            emplist[numEmployee] = employee;
        }
        numEmployee++;
        return true;
    }

    /**
     * This is a helper method that traverses the employee array and returns the
     * index for the employee in the array.
     *
     * @param employee object that is to be found in the array
     * @return index number if the employee is found -1 otherwise
     */
    private int findIndex(Employee employee) {
        for (int i = 0; i < numEmployee; i++) {
            if (emplist[i].getProfile().equals(employee.getProfile())) {
                return i; //gives the index in the array
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets the number of employee in the array.
     *
     * @return the number of employee in the array.
     */
    public int getNumEmployee() {
        return numEmployee;
    }

    /**
     * Removes the employee from the array emplist
     *
     * @param employee object that has to be removed from the array.
     * @return true if the employee is successfully removed and false if it is not
     */
    public boolean remove(Employee employee) {
        empExists = find(employee);
        int index = 0;

        if (empExists == 1) {
            index = findIndex(employee);
            for (int i = index; i < emplist.length - 1; i++) {
                emplist[i] = emplist[i + 1];
            }
            numEmployee--;
            return true;
        }
        return false;
    }

    /**
     * Sets hours for a part time employee
     *
     * @param employee object whose hours have to be set
     * @return true if the hours is successfully set else false.
     */
    public boolean setHours(Employee employee) {
        empExists = findIndex(employee);
        if (emplist[empExists] instanceof Parttime) {
            if (empExists >= 0) {
                ((Parttime) emplist[empExists]).setHours(((Parttime) employee).getHours());
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates payments for employee based on their role
     *
     */
    public void processPayments() {
        if (numEmployee > 0) {
            for (int i = 0; i < numEmployee; i++) {
                if(emplist[i].getPayment() == 0){
                    if (emplist[i] instanceof Fulltime) {
                        if (emplist[i] instanceof Management) {
                            Management mngmntEmp = (Management) emplist[i];
                            mngmntEmp.calculatePayment();
                        } else {
                            Fulltime ftEmp = (Fulltime) emplist[i];
                            ftEmp.calculatePayment();
                        }
                    } else if (emplist[i] instanceof Parttime) {
                        Parttime ptEmp = (Parttime) emplist[i];
                        ptEmp.calculatePayment();
                    }

                }
            }
        }

    }

    /**
     * Creates a string builder for the employees.
     * @return string a string that was built.
     */
    public String print() {
        if (numEmployee == 0) {
            textOut.append("Employee database is Empty");
        }
        if (numEmployee > 0) {
            textOut.append("\n");
            textOut.append("--Printing earning statements for all employees--");
            textOut.append("\n");
            for (int i = 0; i < numEmployee; i++) {
                textOut.append(emplist[i].toString());
                textOut.append("\n");
            }
            textOut.append("--End of list.");
            textOut.append("\n");

        }
        outStr = textOut.toString();

        return outStr;
    }


    /**
     * Helper method implementing selection sort algorithm to sort the dept name strings in lexicographic order
     *
     * @param array contains a copy of each employee's dept from the emplist
     */
    private void selectionSort(String[] array) {
        for (int j = 0; j < array.length - 1; j++) {
            // Find min: the index of the string reference that should go into cell j.
            // Look through the unsorted strings (those at j or higher) for the one that is first in lexicographic order
            int min = j;
            for (int k = j + 1; k < array.length; k++)
                if (array[k].compareTo(array[min]) < 0) min = k;

            // Swap the reference at j with the reference at min
            String temp = array[j];
            array[j] = array[min];
            array[min] = temp;
        }

    }

    /**
     * Creates a string builder for the department the employees work for.
     * @return string that was built.
     */
    public String printByDepartment() {
        String[] arrayOfDept = new String[numEmployee];

        if (numEmployee == 0) {
            textOut.append("Employee database is empty");
        }
        if (numEmployee > 0) {
            textOut.append("--Printing earning statements by department--");
            textOut.append("\n");
            for (int i = 0; i < numEmployee; i++) {
                arrayOfDept[i] = emplist[i].getProfile().getDepartment();
            }

            selectionSort(arrayOfDept);

            for (String element : arrayOfDept) {
                for (int i = 0; i < arrayOfDept.length; i++) {
                    if (element == String.valueOf(emplist[i].getProfile().getDepartment())) {
                        textOut.append(emplist[i].toString());
                        textOut.append("\n");
                    }
                }
            }
            textOut.append("--End of list.");
        }
        outStr = textOut.toString();
         return outStr;
    }

    /**
     * Helper method to sort the dates in ascending order
     * @param sortDate is a copy of the book array so it doesn't affect the actual
     *                 positions in the emplist array
     */
    private void sortByDate(Employee[] sortDate) {
        for (int i = 0; i < numEmployee - 1; i++) {
            int index = i;
            for (int j = i + 1; j < numEmployee; j++) {
                if (sortDate[i + 1] == null) {
                    break;
                }
                if (sortDate[j].getProfile().getDateHired().compareTo((sortDate[index].getProfile().getDateHired())) == -1) {
                    index = j;
                }
            }
            Employee olderEmp = sortDate[index];
            sortDate[index] = sortDate[i];
            sortDate[i] = olderEmp;
        }

    }

    /**
     * Creates a string builder by date hired for the employees.
     * @return string that was built.
     */
    public String printByDate() {
        Employee[] sortDate = new Employee[emplist.length];

        if (numEmployee == 0) {
            textOut.append(".Employee database is empty.");
        }
        if (numEmployee > 0) {
            textOut.append("\n");
            textOut.append("--Printing earning statements by date--");
            textOut.append("\n");

            for (int i = 0; i < numEmployee; i++) {
                sortDate[i] = emplist[i];
            }
            sortByDate(sortDate);
            for (int i = 0; i < numEmployee; i++) {
                textOut.append(sortDate[i].toString());
                textOut.append("\n");
            }
            textOut.append("--End of list.");
        }
        outStr = textOut.toString();
        return outStr;

    }

    /**
     * writes the contents of emplist to the filename selected by the user
     * @param filename the path file selected by user.
     */
    public void exportDatabase(String filename){
        try {
            PrintWriter writer = new PrintWriter(filename);
            for(int i =0; i < numEmployee; i++){
                String output = emplist[i].toString();
                writer.write(output);
                writer.write("\n");
                writer.flush();
            }
            writer.close();

        }  catch (Exception e){
            e.printStackTrace();
        }

    }
}