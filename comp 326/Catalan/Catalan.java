// Writers of the code are:
// 8078082 Talitha van Lith
// 5659163 Jack Baucke
// 1667581 Megan Starnes
// 1791238 Tom Maxted
// Date: 14 May 2019

import java.util.*;

class Catalan { 
    
    public static long catalan(int num) { 
        int c[] = new int[(int)num+1];
        Arrays.fill(c, 0);
          
        // Base case 
        if (num == 0 || num == 1) { 
            return 1; 
        } else {
            c[0] = 1;
            c[1] = 1;
        }

        for (int i = 2; i < num+1; i++) { 
            for (int j = 0; j < i; j++) { 
                c[i] = c[i] + c[j] * c[i-j-1];
            }
        } 
        return c[num-1]; 
    } 
  
    public static void main(String[] args) { 
        int i = 0;
        while (i <= 20){
            System.out.println(catalan(i));
            i++;
        }
    } 
}