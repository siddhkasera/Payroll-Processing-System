package PayrollProcessingApp;

import PayrollProcessing.*;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Controller {

    //bringing in company and employee array
    Company companyDB = new Company();
    StringBuilder str = new StringBuilder();
    public static final int numOfElements = 14;
    public static String name;
    public static String deptName;
    public static String dateHiredStr;
    public static double annualSalary;
    public static double hoursWorked;
    public static int role = 0;
    private int numEmployee = 0;
    private static final int MANAGER = 1;
    private static final int DEPT_HEAD = 2;
    private static final int DIRECTOR = 3;
    private final int MAX_PT_HOURS = 100;


    @FXML
    private ToggleGroup ManagerType;

    @FXML
    private ToggleGroup DeptType;

    @FXML
    private ToggleGroup EmpType;

    @FXML
    private TextField nameFieldID;

    @FXML
    private DatePicker DateHiredID;

    @FXML
    private RadioButton fullTimeRadioID;

    @FXML
    private RadioButton managementRadioID;

    @FXML
    private RadioButton partTimeRadioID;

    @FXML
    private TextField salaryFieldID;

    @FXML
    private RadioButton CSRadioID;

    @FXML
    private RadioButton ECERadioID;

    @FXML
    private RadioButton ITRadioID;

    @FXML
    private Button setHoursButton;

    @FXML
    private TextField hrsWorkedID;

    @FXML
    private TextField rateFieldID;

    @FXML
    private RadioButton managerRadioID;

    @FXML
    private RadioButton deptHeadRadioID;

    @FXML
    private RadioButton directorRadioID;

    @FXML
    private TextArea TextAreaID;

    /**
     * Gathers and checks the selected data from the user after they press the add button
     * to create an employee profile in the Payroll Processing system.
     *
     * @param event handles the event from button click
     */
    @FXML
    void add(ActionEvent event) {

        try {
            name = nameFieldID.getText();
            dateHiredStr = DateHiredID.getValue().toString(); //formatted in yyyy-mm-dd
            System.out.println(dateHiredStr);

            dateHiredStr = formatDate(dateHiredStr); //reformatting date to mm/dd/yyyy


            if (fullTimeRadioID.isSelected()) {

                annualSalary = Double.parseDouble(salaryFieldID.getText());

                if (CSRadioID.isSelected()) {
                    deptName = CSRadioID.getText();
                    addFullTimeEmployee();
                } else if (ITRadioID.isSelected()) {
                    deptName = ITRadioID.getText();
                    addFullTimeEmployee();
                } else if (ECERadioID.isSelected()) {
                    deptName = ECERadioID.getText();
                    addFullTimeEmployee();
                }
            }

            else if (partTimeRadioID.isSelected()) {

                double hourlyPay = Double.parseDouble(rateFieldID.getText());

                if (CSRadioID.isSelected()) {
                    deptName = CSRadioID.getText(); //finding CSRadioButton label name and set that to deptname
                    addPartTimeEmployee(hourlyPay);

                } else if (ITRadioID.isSelected()) {
                    deptName = ITRadioID.getText(); //finding CSRadioButton label name and set that to deptname
                    addPartTimeEmployee(hourlyPay);

                } else if (ECERadioID.isSelected()) {
                    deptName = ECERadioID.getText(); //finding CSRadioButton label name and set that to deptname
                    addPartTimeEmployee(hourlyPay);
                }

            } else if (managementRadioID.isSelected()) {

                annualSalary = Double.parseDouble(salaryFieldID.getText().toString());

                if (CSRadioID.isSelected()) {
                    deptName = CSRadioID.getText(); //finding CSRadioButton label name and set that to deptname
                    addMngmntEmployee();

                } else if (ITRadioID.isSelected()) {
                    deptName = ITRadioID.getText();
                    addMngmntEmployee();

                } else if (ECERadioID.isSelected()) {
                    deptName = ECERadioID.getText();
                    addMngmntEmployee();
                }
            }
        } catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("Please add all employee information.");
            TextAreaID.setText(str.toString());
        }

    }

    /**
     * Helper method that adds an employee of management type into the employee database.
     */
    private void addMngmntEmployee() {
        Profile AMProfile = new Profile(name, deptName, dateHiredStr);
        Management mngmntEmp = new Management(AMProfile, annualSalary, role);
        if (annualSalary < 0) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("Salary cannot be negative.");
            TextAreaID.setText(str.toString());
        } else if (AMProfile.getDateHired().isValid()) {
            if (companyDB.add(mngmntEmp)) {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee added.");

            } else {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee is already in the list.");//print employee is already in the list
            }
            TextAreaID.setText(str.toString());
        } else {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append(mngmntEmp.getProfile().getDateHired().getMonth() + "/" + mngmntEmp.getProfile().getDateHired().getDay() + "/" +
                    mngmntEmp.getProfile().getDateHired().getYear() + " is not a valid date!");
            TextAreaID.setText(str.toString());
        }
    }

    /**
     * Helper method that adds an employee of part-time type into the employee database.
     * @param hourlyPay attribute needed in order to input a part-time employee
     */
    private void addPartTimeEmployee(double hourlyPay) {
        Profile APProfile = new Profile(name, deptName, dateHiredStr);
        Parttime parttimeEmp = new Parttime(APProfile, hourlyPay);
        if (hourlyPay < 0) {
        } else if (APProfile.getDateHired().isValid()) {
            if (companyDB.add(parttimeEmp)) {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee added.");

            } else {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee is already in the list.");//print employee is already in the list
            }
            TextAreaID.setText(str.toString());
        } else {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append(parttimeEmp.getProfile().getDateHired().getMonth() + "/" + parttimeEmp.getProfile().getDateHired().getDay() + "/" +
                    parttimeEmp.getProfile().getDateHired().getYear() + " is not a valid date!");
            TextAreaID.setText(str.toString());
        }
    }

    /**
     * Helper method that adds an employee of full-time type into the employee database.
     */
    private void addFullTimeEmployee() {
        Profile AFProfile = new Profile(name, deptName, dateHiredStr);
        Fulltime fulltimeEmp = new Fulltime(AFProfile, annualSalary);
        if (annualSalary < 0) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("Salary cannot be negative.");
            TextAreaID.setText(str.toString());
        } else if (AFProfile.getDateHired().isValid()) {
            if (companyDB.add(fulltimeEmp)) {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee added.");

            } else {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee is already in the list.");//print employee is already in the list
            }
            TextAreaID.setText(str.toString());
        } else {
            str.append("\n");
            str.append(fulltimeEmp.getProfile().getDateHired().getMonth() + "/" + fulltimeEmp.getProfile().getDateHired().getDay() + "/" +
                    fulltimeEmp.getProfile().getDateHired().getYear() + " is not a valid date!");
            TextAreaID.setText(str.toString());
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void clear(ActionEvent event) {

        try {

            for (int i = 0; i <= numOfElements; i++) {

                if (!nameFieldID.getText().isEmpty())
                    nameFieldID.clear();
                else if (!hrsWorkedID.getText().isEmpty()) {
                    hrsWorkedID.clear();
                } else if (!DateHiredID.getEditor().getText().isEmpty()) {
                    DateHiredID.getEditor().clear();
                } else if (!rateFieldID.getText().isEmpty()) {
                    rateFieldID.clear();
                } else if (!salaryFieldID.getText().isEmpty()) {
                    salaryFieldID.clear();
                }

                if (ManagerType.getSelectedToggle().isSelected()) {
                    managerRadioID.setSelected(false);
                    deptHeadRadioID.setSelected(false);
                    directorRadioID.setSelected(false);

                } else if (DeptType.getSelectedToggle().isSelected()) {
                    ITRadioID.setSelected(false);
                    CSRadioID.setSelected(false);
                    ECERadioID.setSelected(false);

                } else if (EmpType.getSelectedToggle().isSelected()) { //not deselecting for some reason in each group
                    partTimeRadioID.setSelected(false);
                    fullTimeRadioID.setSelected(false);
                    managementRadioID.setSelected(false);
                }
            }
        } catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("There is nothing to clear. Please add information.");
            TextAreaID.setText(str.toString());
        }
    }

    @FXML
    void printAll(ActionEvent event) {
        str.append(companyDB.print());
        if (!TextAreaID.getText().isEmpty()) {
            str.append("\n");
        }
        TextAreaID.setText(str.toString());

    }

    @FXML
    void printByDate(ActionEvent event) {
        str.append(companyDB.printByDate());
        if (!TextAreaID.getText().isEmpty()) {
            str.append("\n");
        }
        TextAreaID.setText(str.toString());

    }

    @FXML
    void printByDept(ActionEvent event) {
        str.append(companyDB.printByDepartment());
        if (!TextAreaID.getText().isEmpty()) {
            str.append("\n");
        }
        TextAreaID.setText(str.toString());


    }

    /**
     * Helper method in order to format date from yyyy-mm-dd to mm/dd/yyyy
     * to ensure proper passing of a String date into Date.java
     *
     * @param date  attribute of String before formatting
     * @return      formatted String for creating employee profile
     */
    private String formatDate(String date) {
        String temp = "";
        StringBuilder dateFormat = new StringBuilder();
        dateHiredStr = DateHiredID.getValue().toString(); //is formatted in yyyy-mm-dd

        //converting the str yyyy-mm-dd to mm/dd/yyyy
        String dateHiredStrArr[] = dateHiredStr.split("-"); //splitting the yyyy-mm-dd
        temp = dateHiredStrArr[0]; //rearranging the str to the formatting we want
        dateHiredStrArr[0] = dateHiredStrArr[1];
        dateHiredStrArr[1] = dateHiredStrArr[2];
        dateHiredStrArr[2] = temp;
        String delimiter = ","; //needed in order to split the array into a string
        dateHiredStr = Arrays.toString(dateHiredStrArr);

        //takes the dateHiredStrArr and builds it into one string using strbuilder
        for (String str : dateHiredStrArr)
            dateFormat.append(str).append(delimiter);
        dateHiredStr = dateFormat.substring(0, dateFormat.length() - 1); //needed to get not have brackets in our final string
        dateHiredStr = dateHiredStr.replaceAll(",", "/"); //replaces commas with the string that will be used as a delim in Date.java

        return dateHiredStr;
    }

    @FXML
    void remove(ActionEvent event) {

        try {
            name = nameFieldID.getText();
            dateHiredStr = DateHiredID.getValue().toString();
            dateHiredStr = formatDate(dateHiredStr);

            numEmployee = companyDB.getNumEmployee();
            if (CSRadioID.isSelected()) {
                deptName = CSRadioID.getText();
            } else if (ITRadioID.isSelected()) {
                deptName = ITRadioID.getText();
            } else if (ECERadioID.isSelected()) {
                deptName = ECERadioID.getText();
            }

            Profile RProfile = new Profile(name, deptName, dateHiredStr);
            Employee removeEmp = new Employee(RProfile);
            if (companyDB.remove(removeEmp)) {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee removed");
            } else if (numEmployee == 0) {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee database is empty");
            } else {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee does not exist");
            }
            TextAreaID.setText(str.toString());

        } catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {

                str.append("\n");
            }
            str.append("Please add all employee information to remove.");
            TextAreaID.setText(str.toString());
        }

    }

    /**
     * Gathers and checks the selected data from GUI in order to Set Hours for a part-time employee
     * after pressing Set Hours button. Creates temporary Parttime employee to verify they exist in employee database.
     *
     * @param event handles the event from button click
     */
    @FXML
    void setHours(ActionEvent event) {

        try {
            name = nameFieldID.getText();
            dateHiredStr = formatDate(dateHiredStr);  //yyyy-mm-dd --> mm-dd-yyyy
            hoursWorked = Double.parseDouble(hrsWorkedID.getText());

            if (CSRadioID.isSelected()) {
                deptName = CSRadioID.getText();
            } else if (ITRadioID.isSelected()) {
                deptName = ITRadioID.getText();
            } else if (ECERadioID.isSelected()) {
                deptName = ECERadioID.getText();
            }

            Profile setHrsProfile = new Profile(name, deptName, dateHiredStr);
            Parttime setHrsEmp = new Parttime(setHrsProfile);
            numEmployee = companyDB.getNumEmployee();
            setHrsEmp.setHours(hoursWorked);

            if (numEmployee == 0) {
                if (!TextAreaID.getText().isEmpty()) {
                    str.append("\n");
                }
                str.append("Employee database is empty.");
            } else if (hoursWorked < 0) {
                str.append("\n");
                str.append("Working hours cannot be negative.");
            } else if (hoursWorked > MAX_PT_HOURS) {
                str.append("\n");
                str.append("Invalid hours: over 100.");
            } else if (setHrsProfile.getDateHired().isValid()) {
                if (companyDB.setHours(setHrsEmp)) {
                    str.append("\n");
                    str.append("Working hours set.");
                } else {
                    str.append("\n");
                    str.append("Employee does not exist.");
                }
            }
            TextAreaID.setText(str.toString());
        } catch (NumberFormatException e) {
            str.append("\n");
            str.append("Please enter the hours worked.");
            TextAreaID.setText(str.toString());
        }

        catch (ArrayIndexOutOfBoundsException e) {
            str.append("\n");
            str.append("Please enter all the employee's information and make sure it is correct.");
            TextAreaID.setText(str.toString());

        }


    }

    @FXML
    void importDB(ActionEvent event) {

        try {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the import"); //check this print statement
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);
        String filePath = sourceFile.getAbsolutePath();
        String fileName = sourceFile.getName();
        String command = "";


            File dbName = new File(filePath);
            Scanner readFile = new Scanner(dbName);
            while (readFile.hasNextLine()) {
                String data = readFile.nextLine();
                String[] arrOfStr = data.split(",");
                command = arrOfStr[0];
                switch (command) {
                    case "P": //handling command add for parttime
                        name = arrOfStr[1];
                        deptName = arrOfStr[2];
                        dateHiredStr = arrOfStr[3];
                        Double hourlyPay = Double.parseDouble(arrOfStr[4]);
                        Profile APProfile = new Profile(name, deptName, dateHiredStr);
                        Parttime parttimeEmp = new Parttime(APProfile, hourlyPay);
                        companyDB.add(parttimeEmp);
                    case "F": //handling command add for full time
                        name = arrOfStr[1];
                        deptName = arrOfStr[2];
                        dateHiredStr = arrOfStr[3];
                        annualSalary = Double.parseDouble(arrOfStr[4]);
                        Profile AFProfile = new Profile(name, deptName, dateHiredStr);
                        Fulltime fulltimeEmp = new Fulltime(AFProfile, annualSalary);
                        companyDB.add(fulltimeEmp);
                    case "M": //handling command add for management
                        name = arrOfStr[1];
                        deptName = arrOfStr[2];
                        dateHiredStr = arrOfStr[3];
                        if (arrOfStr.length > 5) {
                            annualSalary = Double.parseDouble(arrOfStr[4]);
                            role = Integer.parseInt(arrOfStr[5]);
                        }
                        Profile AMProfile = new Profile(name, deptName, dateHiredStr);
                        Management mngmntEmp = new Management(AMProfile, annualSalary, role);
                        companyDB.add(mngmntEmp);
                }
            }
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append(fileName + " imported");
            TextAreaID.setText(str.toString());
            readFile.close();

        } catch (FileNotFoundException e) {
            str.append(e.getMessage());
            e.printStackTrace();

        } catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("No import file selected.");
            TextAreaID.setText(str.toString());
        }


    }

    @FXML
    void exportDB(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the import"); //check this statement.
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        try {
            File targetFile = chooser.showSaveDialog(stage);
            String targetPath = targetFile.getAbsolutePath();
            String targetName = targetFile.getName();
            companyDB.exportDatabase(targetPath);
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append(targetName + " exported");
            TextAreaID.setText(str.toString());

        } catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("No export file selected");
            TextAreaID.setText(str.toString());
        }
    }

    /**
     * Calls the processPayments() method from Company to calculate payments for all employees
     *
     * @param event handles the event from button click
     */
    @FXML
    void calculate(ActionEvent event) {
        numEmployee = companyDB.getNumEmployee();
        if (numEmployee == 0) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("Employee database is empty");
            TextAreaID.setText(str.toString());
        }
        else {
            companyDB.processPayments();
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("Calculation of employees is done.");
            TextAreaID.setText(str.toString());
        }
    }


    /**
     * Sets the role as manager for management employee to add profile correctly
     *
     * @param event handles the event from mouse click on radio button
     */
    @FXML
    void setManager(MouseEvent event) {
        role = MANAGER;
    }

    /**
     * Sets the role as department head for management employee to add profile correctly
     *
     * @param event handles the event from mouse click on radio button
     */
    @FXML
    void setDepartmentHead(MouseEvent event) {
        role = DEPT_HEAD;
    }

    /**
     * Sets the role as director for management employee to add profile correctly
     *
     * @param event handles the event from mouse click on radio button
     */
    @FXML
    void setDirector(MouseEvent event) {
        role = DIRECTOR;
    }


    /**
     * Disables unnecessary functions when user presses Full Time radio button on GUI
     *
     * @param event handles the event from mouse click on radio button
     */
    @FXML
    void setFullTime(MouseEvent event) {

        hrsWorkedID.setDisable(true);
        rateFieldID.setDisable(true);
        setHoursButton.setDisable(true);
        managementRadioID.setDisable(false);
        directorRadioID.setDisable(true);
        deptHeadRadioID.setDisable(true);
        managerRadioID.setDisable(true);
        salaryFieldID.setDisable(false);

    }

    /**
     * Disables unnecessary functions when user presses Full Time radio button on GUI
     *
     * @param event handles the event from mouse click on radio button
     */
    @FXML
    void setManagement(MouseEvent event) {

        rateFieldID.setDisable(true);
        hrsWorkedID.setDisable(true);
        setHoursButton.setDisable(true);
        managementRadioID.setDisable(false);
        managerRadioID.setDisable(false);
        directorRadioID.setDisable(false);
        deptHeadRadioID.setDisable(false);
        salaryFieldID.setDisable(false);

    }

    /**
     * Disables unnecessary functions when user presses Part Time radio button on GUI
     *
     * @param event handles the event from mouse click on radio button
     */
    @FXML
    void setPartTime(MouseEvent event) {

        rateFieldID.setDisable(false);
        hrsWorkedID.setDisable(false);
        setHoursButton.setDisable(false);
        managementRadioID.setDisable(false);
        managerRadioID.setDisable(true);
        directorRadioID.setDisable(true);
        deptHeadRadioID.setDisable(true);
        salaryFieldID.setDisable(true);

    }

}

