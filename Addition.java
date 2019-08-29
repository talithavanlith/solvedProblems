import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

public class Addition {
    private static int remainder;
    private static int[] sum;
    private static int[] quo;

    public static void add_numbers(int[] a, int[] b, int base){
        int c, n, i;
        remainder = 0;
        
        for(i = 1; i <= sum.length; i++){

            //add the two last numbers together with any remainder
            c = a[a.length-i] + b[b.length-i] + remainder;

            //check to see if the numbers overflow
            if(c >= base){
                n = c - base;
                remainder = 1;
            }else{
                n = c;
                remainder = 0;
            }

            //add the number to the final sum
            sum[sum.length - i] = n;
        }

    }

    public static void divide_by_10(int base){
        quo = new int[sum.length -1];
        quo = Arrays.copyOfRange(sum, 0, sum.length-1);
        remainder = sum[sum.length-1];

    }

    public static void divide_by_2(int base){
        int[] divide_array = new int[base];
        quo = new int[sum.length];
        int n;

        // creating divide array using base and * 2
        for(int i =0; i<base; i++){
            n = i * 2;
            if(n >= base){
                n = n - base + 10;
            }
            divide_array[i] = n;
        }

        remainder = 0;
        // divide by 2 by iterating through sum
        for(int i =0; i<sum.length; i++){
            n = sum[i] + (remainder * 10);
            if(n == 10){
                n = base;
            }

            // iterate through divide_array to find a number that i can be divided by
            for (int r = base-1; r >= 0; r--) {
                if(divide_array[r] == 0 || divide_array[r] <= n ){
                    quo[i] = r;
                    remainder = n - divide_array[r];
                    r = -1; // terminate loop
                }
            }
        }

    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] a, b;
        int aLen, bLen;
        String aStr, bStr;
        int base;

        base = scanner.nextInt();
        scanner.nextLine();
        aStr = scanner.nextLine();
        bStr = scanner.nextLine();
        
        aLen = aStr.length();
        bLen = bStr.length();

        //set length of sum array to the longer number 
        if(aLen > bLen){
            sum = new int[aLen + 1];
        }else if(bLen > aLen){
            sum = new int[bLen + 1];
        }else{
            sum = new int[aLen + 1]; //they are equal
        }

        a = new int[sum.length];
        b = new int[sum.length];
        
        //make number arrays from strings
        for (int i = 1; i < sum.length; i++) {
            if (i <= aLen){
                a[sum.length - i] = Character.getNumericValue(aStr.charAt(aLen - i));
            }
            if (i <= bLen){
                b[sum.length - i] = Character.getNumericValue(bStr.charAt(bLen - i));
            }
        }
        
        //perform addition
        add_numbers(a, b, base);

        // print result from addition
        if(sum[0] == 0){
            sum = Arrays.copyOfRange(sum, 1, sum.length);
        }
        for (int i = 0; i < sum.length; i++) {
            System.out.print(sum[i]);
        }
        System.out.println();

        //perform division by 2
        if(base == 2){
            divide_by_10(base);
        }else{
            divide_by_2(base);
        }

        //print quotient
        if(quo[0] == 0){
            quo = Arrays.copyOfRange(quo, 1, quo.length);
        }
        for (int i = 0; i < quo.length; i++) {
            System.out.print(quo[i]);
        }
        System.out.println();

        //print remainder
        System.out.println(remainder);
        
    }
}