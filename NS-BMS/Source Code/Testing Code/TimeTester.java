import java.io.FileNotFoundException;
 
public class TimeTester 
{
    
    public static void main(String[] args) throws FileNotFoundException
    {
    	
    	// TODO, add your application code
    	Time yolo = new Time(7, 0, 14, 30); //hours are 7:00 to 2:30, creates file
    	Time swag = new Time(); //loads from file
    	System.out.println(swag.getTime());
    	System.out.println(swag.isAuthorized()); //true if in school, false if outside of it
    	System.out.println(swag.changeHours("16", "8", "8", "9")); //false because store opens after it closes
    	System.out.println(swag.changeHours("6", "7", "15", "30"));  
    	System.out.println(swag.changeHours("7", "0", "14", "30"));
    	
    }
}
