import java.util.Scanner;                                                       
                                                                                
/* File: etude07.java - Feb 2019 */                                             
                                                                                
                                                                                
/**                                                                             
 *  Method to validate an email address for InsuroCorp.                         
 *                                                                              
 *  @author Talitha van Lith                                                    
 */                                                                             
public class Etude07 {                                                          
                                                                                
    public static void main(String args[]){
        String emailToCheck;                                                    
        String outputEmail = "";
        Scanner sc = new Scanner(System.in);

        //scan in email to check until no more emails are entered
        while (sc.hasNextLine()){
            emailToCheck = sc.nextLine();                                        
                                                                                    
            //convert to lower case                                                 
            outputEmail = emailToCheck.toLowerCase();
            
            //replace all _dot_ 
            outputEmail = outputEmail.replace("_dot_", ".");

            //check @ symbol
            outputEmail = checkAtSymbol(outputEmail, emailToCheck);
            
            // checking extension
            if((outputEmail.length() != 0)){
                
                // if extension and ip address is incorrect email is incorrect
                if(!(checkExtension(outputEmail)) && !(checkIP(outputEmail))){
                    System.out.println(emailToCheck + " <- Invalid extension");
                }else{

                    if((checkMailbox(outputEmail, emailToCheck)) && (checkDomain(outputEmail, emailToCheck))){
                        System.out.println(outputEmail);
                    }
                    
                }

            }
        }
        sc.close();
    }
    
    public static String checkAtSymbol(String e, String original) {
        String outputEmail = "";
        //check for @ present
        if(!(e.contains("@"))){
            if(e.contains("_at_")){
                int lastAt = e.lastIndexOf("_at_");
                outputEmail = e.substring(0, lastAt);
                outputEmail += "@";
                outputEmail += e.substring(lastAt + 4);
            }else{
                System.out.println(original + " <- Missing @ symbol");
            }
        }else{
            outputEmail = e;
        }
        return outputEmail;
    }                                                                           
                                                                                
    public static Boolean checkExtension(String e) {
        String[] extensions = {".co.nz", ".com.au", ".co.ca", ".com", ".co.us", ".co.uk"};

        for(String ex: extensions){
            if(e.endsWith(ex)){
                return true;
            }
        }
        return false;                                                                 
    }  

    public static Boolean checkIP(String e) {
        if(e.contains("@[") && e.endsWith("]")){
            int i = e.indexOf("[");
            String ip = e.substring(i+1, e.length()-1);
            //regex from mdma at StackOverflow
            if(ip.matches("(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))")){
                return true;
            }
        }                                      
        return false;
    } 
    
    public static Boolean checkMailbox(String e, String original) {
        int i = e.indexOf("@");
        String mailBox = e.substring(0,i);
        int lastChar = mailBox.length() -1;

        if(mailBox.matches("[A-Za-z0-9\\.\\-\\_]*")){
            
            //check that first value isn't .-_
            if(mailBox.charAt(0) == '.' || mailBox.charAt(0) == '-' || mailBox.charAt(0) == '_'){
                System.out.println(original + " <- Mailbox cannot start with a symbol");
                return false;
            }

            //check that last value isn't .-_
            if(mailBox.charAt(lastChar) == '.' || mailBox.charAt(lastChar) == '-' || mailBox.charAt(lastChar) == '_'){
                System.out.println(original + " <- Mailbox cannot end with a symbol");
                return false;
            }

            //check for double ._- in a row
            for(int j =0; j <= lastChar; j++){
                char ch = mailBox.charAt(j);
                if( ch == '.' || ch == '-' || ch == '_'){
                    char nextCh = mailBox.charAt(j+1);
                    if(nextCh == '.' || nextCh == '-' || nextCh == '_'){
                        System.out.println(original + " <- Mailbox cannot have more than one symbol in a row");
                        return false;
                    }
                }
            }
                
            return true;
        }
        
        System.out.println(original + " <- Mailbox contains invalid symbols");
        return false;
    }

    public static Boolean checkDomain(String e, String original) {
        int i = e.indexOf("@");
        String domain = e.substring(i+1);

        if(domain.startsWith("[")&& e.endsWith("]")){
            return true;
        }

        if(domain.matches("[A-Za-z0-9\\.]*")){
            
            //check for .. or first char being a dot
            if(domain.contains("..") || domain.charAt(0) == '.'){
                System.out.println(original + " <- Domain cannot start with a . or have ..");
                return false;
            }
                
            return true;
        }
        
        System.out.println(original + " <- Domain contains invalid symbols");
        return false;
    }
                                                                                
} 