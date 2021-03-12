package PayrollProcessingApp;

import PayrollProcessing.*;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//<BorderPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/15.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="PayrollProcessingApp.Controller">

public class Controller {


    //bringing in company and employee array
    Company companyDB = new Company();
    DatePicker datePick = new DatePicker();
    // LocalDate dateHired = datePick.getValue();
    //idk how to use this yet but I think it is how appending the strings will work
    StringBuilder str = new StringBuilder();
    public static final int numOfElements = 14;
    public static String name;
    public static String deptName;
    public static String dateHiredStr;
    public static double annualSalary;
    public static int role = 0;


    @FXML
    private GridPane gridPaneTab1;

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
    private Button clearButtonID;

    @FXML
    private Button addButtonID;

    @FXML
    private Button removeButtonID;

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
    private Button printDeptID;

    @FXML
    private Button printDateID;

    @FXML
    private Button printAllID;

    @FXML
    private MenuBar menuBarDBID;

    @FXML
    private MenuItem closeButtonID;

    @FXML
    private MenuItem importButtonID;

    @FXML
    private MenuItem exportButtonID;

    @FXML
    private TextArea TextAreaID;


    @FXML
    void add(ActionEvent event) {

        try {
            name = nameFieldID.getText();
            dateHiredStr = DateHiredID.getValue().toString(); //formatted in yyyy-mm-dd

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

            if (partTimeRadioID.isSelected()) {

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
        }
        catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("There is nothing to clear. Please add information.");
            TextAreaID.setText(str.toString());
        }
    }

    @FXML
    void printAll(ActionEvent event) {

    }

    @FXML
    void printByDate(ActionEvent event) {

    }

    @FXML
    void printByDept(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void setHours(ActionEvent event) {

    }

    @FXML
    void setCS(MouseEvent event) {

    }

    @FXML
    void close(ActionEvent event) {


    }

    @FXML
    void importDB(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        Label newLabel = new Label();
        chooser.setTitle("Open Source File for the import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);
        String fileName = sourceFile.getAbsolutePath();
        String command = "";
        //System.out.println("the file path  is "+ fileName);

        try {
            File dbName = new File(fileName);
            Scanner readFile = new Scanner(dbName);
            while (readFile.hasNextLine()) {
                String data = readFile.nextLine();
                String[] arrOfStr = data.split(",");
                command = arrOfStr[0];
                // System.out.println("Command is:"+ command);
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
                //System.out.println("The company db is");

                //System.out.println("the line in the file is:"+data);
            }
            //companyDB.print();
            readFile.close();

        } catch (FileNotFoundException e) {
            System.out.println("The error message is" + e.getMessage());
            e.printStackTrace();
        }
            catch (NullPointerException e) {
            if (!TextAreaID.getText().isEmpty()) {
                str.append("\n");
            }
            str.append("No import file selected");
            TextAreaID.setText(str.toString());
        }


    }

    @FXML
    void exportDB(ActionEvent event) {


    }


    @FXML
    void setDate(MouseEvent event) {

    }

    @FXML
    void setManager(MouseEvent event) {

        role = 1;

    }

    @FXML
    void setDepartmentHead(MouseEvent event) {


        role = 2;
    }

    @FXML
    void setDirector(MouseEvent event) {

        role = 3;
    }

    @FXML
    void setECE(MouseEvent event) {

    }


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

    @FXML
    void setIT(MouseEvent event) {

    }

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


