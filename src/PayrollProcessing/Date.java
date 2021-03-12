package PayrollProcessing;

import java.util.Calendar;

/**
 * This class defines the abstract data type sample.Date which encapsulates the data
 * fields and methods of a sample.Date.
 *
 * @author Siddhi Kasera, Sonal Madhok
 **/
public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int month31 = 31;
    public static final int month30 = 30;
    public static final int month28 = 28;
    public static final int month29 = 29;
    public static final int startYear = 1990;
    public static final int endYear = 2021;

    /**
     * Takes mm/dd/yyyy and creates a sample.Date object
     *
     * @param date string to store the month, day amd year
     */
    public Date(String date) {
        String[] arrOfDate = date.split("/"); //Split the string from date variable
        String monthStr = arrOfDate[0];
        monthStr = monthStr.strip();

        month = Integer.parseInt(monthStr);
        day = Integer.parseInt(arrOfDate[1]);
        year = Integer.parseInt(arrOfDate[2]);
    }

    /**
     * No Parameter Constructor creates an object with today's date.
     */
    public Date() {
        Calendar today = Calendar.getInstance(); //gives instance of today's date

        day = today.get(Calendar.DAY_OF_MONTH);
        month = today.get(Calendar.MONTH) + 1;
        year = today.get(Calendar.YEAR);
    }

    /**
     * Compare the elements of a date with another date object i.e year, month and
     * day.
     *
     * @param date the other date.
     * @return -1 if the current year, day or month are less than object date year,
     * month and day 1 if current year, day or month are greater than object
     * date year, day or month 0 if current year, day or month are equal to
     * the object date year, day or month
     */
    @Override
    public int compareTo(Date date) {
        if (year < date.year) {
            return -1;
        } else if (year == date.year) {
            if (month < date.month) {
                return -1;
            } else if (month == date.month) {
                if (day < date.day) {
                    return -1;
                } else if (day == date.day) {
                    return 0;
                } else if (day > date.day) {
                    return 1;
                }
            } else if (month > date.month) {
                return 1;
            }
        } else if (year > date.year) {
            return 1;
        }
        return 0;
    }

    /**
     * getter method returns the day instance variable
     *
     * @return day attribute of sample.Date object
     */
    public int getDay() {
        return day;
    }

    /**
     * getter method returns the month instance variable
     *
     * @return month attribute of the sample.Date object
     */
    public int getMonth() {
        return month;
    }

    /**
     * getter method returns the year instance variable
     *
     * @return year attribute of the sample.Date object
     */
    public int getYear() {
        return year;
    }

    /**
     * Checks if the date entered is valid.
     *
     * @return true if the date is valid with the correct day in a particular month for a year otherwise false
     */
    public boolean isValid() {

        boolean leap = false;
        if (year < startYear || year > endYear) {
            return false;
        }

        Date futureDate = new Date();
        int newDay = futureDate.getDay(); //9
        int newMonth = futureDate.getMonth(); //2
        int newYear = futureDate.getYear(); //2021

        if (year == newYear) {
            if (month > newMonth) {
                return false;
            } else if (month == newMonth) {
                if (day > newDay)
                    return false;
            }
        }

        //checking is a year is leap year.
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    leap = true;
                } else {
                    leap = false;
                }
            } else {
                leap = true;
            }
        } else {
            leap = false;
        }

        //to check if day number is correct for a given month.
        switch (month) {

            case Calendar.JANUARY + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;
            case Calendar.FEBRUARY + 1:
                if (leap) {
                    if (day < 1 || day > month29) {
                        return false;
                    }
                } else if (day < 1 || day > month28) {
                    return false;
                }

                break;
            case Calendar.MARCH + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;
            case Calendar.APRIL + 1:
                if (day < 1 || day > month30) {
                    return false;
                }
                break;

            case Calendar.MAY + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;

            case Calendar.JUNE + 1:
                if (day < 1 || day > month30) {
                    return false;
                }
                break;

            case Calendar.JULY + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;

            case Calendar.AUGUST + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;

            case Calendar.SEPTEMBER + 1:
                if (day < 1 || day > month30) {
                    return false;
                }
                break;

            case Calendar.OCTOBER + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;

            case Calendar.NOVEMBER + 1:
                if (day < 1 || day > month30) {
                    return false;
                }
                break;
            case Calendar.DECEMBER + 1:
                if (day < 1 || day > month31) {
                    return false;
                }
                break;

            default:
                return false;
        }
        return true;
    }
}
