import java.util.ArrayList;

public class LoopyNumbers {
    static int numSent = 0;
    static int largestLoop = 0; 
    static int count;
    static int terminate;
    static ArrayList<Integer> loops = new ArrayList<Integer>();
    static int[] sums = new int[12000001];
    
    public static void main(String[] args) {
        //fill sum array with sums of factors
        for (int i=1; i<=12000000; i++) {
            for (int j=i;j<=12000000; j+=i) {
                if (i != j) {
                    sums[j]+= i;
                }
            }
        }

        for (int i = 1; i <= 9000000; i++) {
            numSent = i;
            terminate = 0;
            findLoops(i);
        }
        //sort loopy numbers
        loops.sort(null);

        //print loopy numbers
        for (int i = 0; i < loops.size(); i++) {
            System.out.println(loops.get(i));
        }
        System.out.println("");

        //largest loop found
        System.out.println(largestLoop);
        //number of loops
        System.out.println(count);
    }

    public static void findLoops(int n) {
        int sum;
        terminate++;

        //if loop too long or number too high
        if (terminate > 28 || n < 1) {
            return;
        }else if (n <= 12000000) {
            sum = sums[n];
        } else {
            return;
        }

        //if prime or happy number
        if (sum == 1 || sum == n) {
            return;
        }

        //if a loop
        if (sum == numSent) {
            loops.add(numSent);
            count++;
            if (largestLoop < terminate) {
                largestLoop = terminate;
            }
            return;
        }

        
        findLoops(sum);
    }
}