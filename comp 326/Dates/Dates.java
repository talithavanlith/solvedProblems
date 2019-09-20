import java.util.*;
import java.lang.*;

public class Dates {
    static String dateToCheck;

    static String day;
    static String month;
    static String year;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //scan in date to check until no more dates are entered
        while (sc.hasNextLine()){
            dateToCheck = sc.nextLine();
            
            if(seperateDate() != false && checkYear() != false && 
                checkMonth() != false && checkDay() != false){

                System.out.println(day + " " + month + " " + year);
            }

        }
        sc.close();

    }

    public static boolean seperateDate() {
        char seperater = 'n';
        int[] count = new int[2];

        // check seperator
        for (int i = 0; i < dateToCheck.length(); i++) {
            char ch = dateToCheck.charAt(i);

            if(ch == '-' || ch == '/' || ch == ' '){

                // if seperator isn't specified yet
                if(seperater == 'n'){
                    seperater = ch;
                    count[0] = i;

                // if seperators are the same and there 
                // have only been one instance so far
                }else if(seperater == ch && count[1] == 0){
                    count[1] = i;

                }else{
                    System.out.println(dateToCheck + " - INVALID: Incorrect seperators.");
                    return false;
                }

            }
        }

        // if there are not exactly 2 seperators
        if(count[1] == 0){
            System.out.println(dateToCheck + " - INVALID: Incorrect date format.");
            return false;
        }

        day = dateToCheck.substring(0, count[0]);
        month = dateToCheck.substring(count[0] + 1, count[1]);
        year = dateToCheck.substring(count[1] + 1);

        return true;
    }

    public static boolean checkYear() {
        int yearInt;

        if(year.matches("[0-9]+") && (year.length() == 2 || year.length() == 4)){
            yearInt = Integer.parseInt(year);
        }else{
            System.out.println(dateToCheck + " - INVALID: Year is not a four or two digit number.");
            return false;
        }

        if(yearInt < 100 && yearInt >= 50){
            yearInt = 1900 + yearInt;
        }else if (yearInt < 50){
            yearInt = 2000 + yearInt;
        }

        if(yearInt > 3000 || yearInt < 1753){
            System.out.println(dateToCheck + " - INVALID: Year out of range.");
            return false;
        }

        year = Integer.toString(yearInt);
        return true;
    }

    public static boolean checkMonth() {

        if (month.length() == 1){
            month = '0' + month;

        }else if (month.length() == 3){

            Character l1 = new Character(month.charAt(0));
            Character l2 = new Character(month.charAt(1));
            Character l3 = new Character(month.charAt(2));

            //checking if all lower case, all upper case or if it's capitalized
            if((Character.isUpperCase(l1) && Character.isUpperCase(l2) && Character.isUpperCase(l3)) 
                || (Character.isLowerCase(l1) && Character.isLowerCase(l2) && Character.isLowerCase(l3))
                || (Character.isUpperCase(l1) && Character.isLowerCase(l2) && Character.isLowerCase(l3))){
                
                char m1 = Character.toUpperCase(l1);
                char m2 = Character.toLowerCase(l2);
                char m3 = Character.toLowerCase(l3);
                
                month = Character.toString(m1) + Character.toString(m2) + Character.toString(m3);

            }else{
                System.out.println(dateToCheck + " - INVALID: Incorrect month format.");
                return false;
            }

        }else if (month.length() != 2){
            System.out.println(dateToCheck + " - INVALID: Incorrect month format.");
            return false;
        }

        // check values are valid and make them uniform
        switch (month) {
            case "01": case "Jan":
                month = "January";
                break;
            case "02": case "Feb":  
                month = "Feb";
                break;
            case "03": case "Mar":  
                month = "Mar";
                break;
            case "04": case "Apr":  
                month = "Apr";
                break;
            case "05": case "May":  
                month = "May";
                break;
            case "06": case "Jun":  
                month = "Jun";
                break;
            case "07": case "Jul":  
                month = "Jul";
                break;
            case "08": case "Aug":  
                month = "Aug";
                break;
            case "09": case "Sep":  
                month = "Sep";
                break;
            case "10": case "Oct":  
                month = "Oct";
                break;
            case "11": case "Nov":  
                month = "Nov";
                break;
            case "12": case "Dec":  
                month = "Nov";
                break;
            default: 
                System.out.println(dateToCheck + " - INVALID: Month provided does not exist.");
                return false;
        }

        return true;
    }

    public static boolean checkDay() {
        int dayInt;

        if(day.matches("[0-9]+") && (day.length() <= 2)){
            dayInt = Integer.parseInt(day);
        }else{
            return false;
        }

        if(month == "Sep" || month == "Apr" || month == "Jun" || month == "Nov"){

            if(dayInt > 30 || dayInt < 1){
                System.out.println(dateToCheck + " - INVALID: Day provided does not exist.");
                return false;
            }
        }else if(month == "Feb"){
            Integer intY = Integer.parseInt(year);

            //if it is a leap year
            if(intY % 4 == 0 && !(intY % 100 == 0 && intY % 400 != 0)){
                if(dayInt > 29 || dayInt < 1){
                    System.out.println(dateToCheck + " - INVALID: Day provided does not exist.");
                    return false;
                }
            // if its not a leap year
            }else if(dayInt > 28 || dayInt < 1){
                System.out.println(dateToCheck + " - INVALID: Day provided does not exist.");
                return false;
            }

        }else if(dayInt > 31 || dayInt < 1){
            System.out.println(dateToCheck + " - INVALID: Day provided does not exist.");
            return false;
        }

        if(dayInt < 10){
            day = "0" + Integer.toString(dayInt);
        }else{
            day = Integer.toString(dayInt);
        }

        return true;
    }

}