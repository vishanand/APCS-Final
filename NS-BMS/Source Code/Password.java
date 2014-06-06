import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

//This class checks if a password is correct, and it also manages the passwords using a text file named PASS.set
public class Password implements Saveable {
	//Creates password object, defaults to "password" if file not found
	public Password(){
            File filePath = new File("config/PASS.set");
            if (filePath.exists()){
                loadFromFile("config/PASS.set");
            }
            else{                
                hash = "password".hashCode();
                saveToFile("config/PASS.set");
            }
	}
        //second constructor that accepts password as input, if the PASS.set file becomes lost
        public Password(String pass){
                hash = pass.hashCode();
                saveToFile("config/PASS.set");
        }
	//Checks if the given password is correct, returns true if so, returns false if not correct
	public boolean checkPassword(String word){
		if (word.hashCode() == hash){
                    return true;
                }
                return false;
	}
	//changes the password and returns false if given existing password is correct, if it isn't, then returns false
	public boolean changePassword(String newPass, String oldPass)
        {
		if (oldPass.hashCode() == hash){
                    hash = newPass.hashCode();
                    return saveToFile("config/PASS.set");
                }
                return false;
	}
        //saves password to a file
	public boolean saveToFile(String filePath) {
            try{
                PrintWriter out = new PrintWriter(filePath);
                Random rnd = new Random();
                out.print(rnd.nextInt(8999)+1000);//random  num from 1000 to 9999, to salt the hash
                out.print(hash);
                out.close();
                return true;
            }
            catch (FileNotFoundException e){
                return false;
            }
        }
        //loads password from a file
        public boolean loadFromFile(String filePath) {
            try{
            FileReader reader = new FileReader(filePath);
                Scanner in = new Scanner(reader);
                String num = in.next();
                num = num.substring(4); //removes first 4 digits, to counter the salt from the save method
                hash = Integer.parseInt(num);
                in.close();
                return true;
            }
            catch (FileNotFoundException e){
                return false;
            }
        }
        
	private int hash; //stores the hashcode for the password
}
