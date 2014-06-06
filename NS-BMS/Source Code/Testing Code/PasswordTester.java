import java.io.FileNotFoundException;
public class PasswordTester
{
    
    public static void main(String[] args) throws FileNotFoundException
    {
    	
    	/**
    	 This class tests the methods of the password class. 
    	 **/
    	Password swag = new Password("swag");
    	System.out.println(swag.checkPassword("swag"));
    	swag.changePassword("yolo", "swag");
    	System.out.println(swag.checkPassword("swag"));
    	System.out.println(swag.checkPassword("yolo"));
    	swag.changePassword("swag", "awesome");
    	System.out.println(swag.checkPassword("awesome"));
    	System.out.println(swag.checkPassword("swag"));
    	
    }
}
