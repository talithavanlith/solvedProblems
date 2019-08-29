import java.util.ArrayList;
import java.util.Scanner;

public class CountingUp {

    // initialise 'global' variables n and k
    public static long n = 0;
    public static long k = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        //while there is a next calculation to make
        while(sc.hasNext()){
            //initialise the top and bottom number arraylists
            ArrayList<Long> topNum = new ArrayList<Long>();
            ArrayList<Long> bottomNum = new ArrayList<Long>();

            // initialise the final number
            long topfinal = 1;
            long bottomfinal = 1;

            // scan in n and k values from scanner
            n = Long.parseLong(sc.next());
            k = Long.parseLong(sc.next());

            // if the values are equal the result is 1
            if (n == k){
                System.out.println("1");
            } else if (n - k < 0){
                System.out.println("-1");
            } else {
                // make arraylist with n factorials
                top(n, topNum);

                // make arraylist with k factorials
                bottom(k, bottomNum);

                // call function to cancel out bottom values until none
                while (bottomNum.size()>1){
                    simplify(topNum, bottomNum);
                }

                //multiply the remaining top arraylist values
                for (int i = 0; i < topNum.size(); i++){
                    topfinal *= topNum.get(i);
                }

                // print result to out
                System.out.println(topfinal);
            }
        }
    }

    //calculate the greatest common divisor of 2 numbers and return it
    public static long GCD(long a, long b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }

    // simplify 2 arraylists
    public static void simplify(ArrayList<Long> topNum, ArrayList<Long> bottomNum){
        for (int i = 0; i < topNum.size(); i++){ // for every top num
            for (int j = 0; j < bottomNum.size(); j++){ // for every bottom num
                long gcd = GCD(topNum.get(i), bottomNum.get(j)); // find gcd for 2 nums
                if (gcd > 1){ // if there is a gcd not 1
                    topNum.set(i, topNum.get(i)/gcd); // set top num / gcd
                    if (bottomNum.get(j)/gcd > 1){ // if bottom divided isnt 1 
                        bottomNum.set(j, bottomNum.get(j)/gcd); // set to /gcd
                    } else { // if bottomnum / gcd = 1
                        bottomNum.remove(j); // remove it
                    }
                }
            }
        }
    }

    // add the top factorial values to array
    public static long top(long num, ArrayList<Long> topNum) {
        // if can be cancelled from the bottom dont add and return
        if(num == (n-k)){
            return 1; 
        }else{
            topNum.add(num);
            top(num-1, topNum);
        } 
        return 1;
    }  

    // add all the bottom factorial values to array
    public static long bottom(long num, ArrayList<Long> bottomNum) {
        if(num == 0){
            return 1; 
        }else{
            bottomNum.add(num);
            bottom(num-1, bottomNum);
        } 
        return 1;
    }
}