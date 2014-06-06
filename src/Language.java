
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//This class allows GUI elements to be translated
public class Language implements Saveable{
    //precondition: filepath exists and is a valid language file
    //creates language object from filepath
    public Language(String file){
        loadFromFile("config/"+file);
    }
    
    //loads phrases of a language from file
    //precondition: file exists and is valid language file
    public boolean loadFromFile(String path){
        try{
            File filePath = new File(path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath), "UTF-8"); 
            //switched to input stream reader to allow forcing of character set, because accents weren't working before
            Scanner in = new Scanner(reader);
            int numPhrases = Integer.parseInt(in.nextLine());
            phrases = new String[numPhrases];
            for (int i = 0; i < numPhrases; i++){ //reads lines from file
                phrases[i] = in.nextLine();
            }
            return true;
        }
        catch (FileNotFoundException | UnsupportedEncodingException e){ //catches both possible exceptions
            return false;
        }
    }
    
    //precondition: a static constant from the language class must be given
    //gets a phrase in this language
    public String getPhrase(int phraseConstant){
        return phrases[phraseConstant];
    }
    
    //this class is not designed to save to a file, so it returns false
    //signalling failed save operation
    public boolean saveToFile(String path){
        return false;
    }
    
    private String[] phrases;
    //the following phrase constants allow a nice interface for the class to
    //external classes, a phrase can be given using one of these human readable
    //phrases instead of the line numbers that they represent
    public static final int BEGIN_SHOPPING_IN_LANGUAGE = 0;
    public static final int SCAN_OR_CHOOSE = 1;
    public static final int SCAN_BARCODE = 2;
    public static final int SCAN = 3;
    public static final int ENTER_NUM_BOOKS = 4;
    public static final int END_SHOPPING = 5;
    public static final int LEAVE_BILL = 6;
    public static final int BILL_TITLE = 7;
    public static final int AUTHOR_LINKER = 8;
    public static final int CURRENCY_SIGN = 9;
    public static final int TOTAL_PRICE = 10;
    public static final int CUSTOMER = 11;
    public static final int ITEM_ADDED_TO_CART = 12;
    public static final int INSUFFICIENT_QUANTITY = 13;
    public static final int ISBN_SCAN_ERROR = 14;
    public static final int STORE_CLOSED = 15;
}
