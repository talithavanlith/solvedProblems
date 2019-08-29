import java.util.*;                                                                                
/* File: Arithmetic.java - May 2019 */

/**
 *  Etude 10: Arithmetic                                                                         
 *  @author Talitha van Lith   8078082                                                 
 */                                                                             
public class Arithmetic {
    private static int goal; // the goal to reach
    private static String result; // the formatted result for printing
    private static ArrayList<Integer> numbers = new ArrayList<Integer>(); // the numbers read in
                                                                                
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        //scan in lines until there is no more lines to read
        while (sc.hasNextLine()){
            numbers.clear();
            result = "";
            goal = 0;
            
            String firstLine = sc.nextLine();
            String secondLine = sc.nextLine();

            // getting goal and type from text
            String nums[] = firstLine.split(" ");
            for (String n : nums) {
                numbers.add(Integer.parseInt(n));
            }
                 
            //split type from goal
            goal = Integer.parseInt(secondLine.substring(0, secondLine.length()-2));
            char type = secondLine.charAt(secondLine.length()-1);
            
            if(numbers.size() == 1){
                System.out.println(type + " " + goal + " " + numbers.get(0));
                type = 'O';
            }

            String ops = "";
            // if type is linear order of operations
            if(type == 'L'){
                if(linear(0, ops) == true){
                    // print out results
                    System.out.println(type + " " + goal + " " + result);
                }else{
                    System.out.println(type + " " + goal + " impossible");
                }

            //if type is the normal order of operations
            }else if(type == 'N'){
                if(normal(0, ops) == true){
                    // print out results
                    System.out.println(type + " " + goal + " " + result);
                }else{
                    System.out.println(type + " " + goal + " impossible");
                }

            }else if(type == 'O'){
                continue;

            }else{
                System.out.println("Please enter a valid Arithmetic type (L or N)");
            }

        }

        sc.close();
    }
    
    public static Boolean linear(int i, String ops) {
        int total = numbers.get(0); // set result to first number

        // work out running total
        for (int j = 0; j < ops.length(); j++){
            if (ops.charAt(j) == '+'){ // if the operand is a plus
                total += numbers.get(j+1); // add the next number to result
            } else{
                total *= numbers.get(j+1); // multiply the result by the next number
            }
        }

        // if all they numbers have been checked
        if (i == (numbers.size()-1)){ 
            if (goal == total){ // if goal is found
                return true; // worked
            } else {
                return false; // failed
            }
        } else if (goal < total){
            return false;
        }

        //Search addition branch
        if (linear(i + 1, (ops + "+"))){
            // result is already found
            if(result != ""){
                return true;
            }
            ops += "+";
            int j;

            //format answer to be printed
            for (j = 0; j<ops.length(); j++) {
                result += numbers.get(j) + " " + ops.charAt(j) + " ";
            }
            result += numbers.get(j);

            return true;

        //Search multiplication branch
        } else if (linear(i + 1, (ops + "*"))){
            // result is already found            
            if(result != ""){
                return true;
            }
            ops += "*";
            int j;

            //format answer to be printed
            for (j = 0; j<ops.length(); j++) {
                result += numbers.get(j) + " " + ops.charAt(j) + " ";
            }
            result += numbers.get(j);
            return true;
        }
        // if we get here it's impossible
        return false;
    }

    public static Boolean normal(int i, String ops) {
        // copy numbers for array to manipulate
        int newArray[] = new int[numbers.size()];
        for (int j = 0; j < numbers.size(); j++){
            newArray[j] = numbers.get(j);
        }

        int total = 0;

        // calculate running total
        for (int j = 0; j < ops.length(); j++){
            if (ops.charAt(j) == '*'){ // if the operand is *
                //multiply values then store in temp array
                int temp = newArray[j] * newArray[j+1];
                newArray[j] = 0;
                newArray[j+1] = temp;
            }
        }
        
        //add up remaining values
        for(int j = 0; j < newArray.length; j++){
            total += newArray[j];
        }

        // if all they numbers have been checked
        if (i == (numbers.size()-1)){ 
            if (goal == total){ // the goal is found
                return true; 
            } else {
                return false; // failed
            }
        }
        // } else if (goal+2 < total){
        //     return false;
        // }

        //Search addition branch
        if (normal(i + 1, (ops + "+"))){
            // result is already found            
            if(result != ""){
                return true;
            }
            ops += "+";
            //format answer to be printed
            int j;
            for (j = 0; j<ops.length(); j++) {
                result += numbers.get(j) + " " + ops.charAt(j) + " ";
            }
            result += numbers.get(j);
            return true;
        //Search multiplication branch
        } else if (normal(i + 1, (ops + "*"))){
            // result is already found            
            if(result != ""){
                return true;
            }
            ops += "*";
            //format answer to be printed
            int j;
            for (j = 0; j<ops.length(); j++) {
                result += numbers.get(j) + " " + ops.charAt(j) + " ";
            }
            result += numbers.get(j);
            return true;
        }
        // if we get here it's impossible
        return false;
    }
}