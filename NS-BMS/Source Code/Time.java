import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
//TODO: implement the load and save methods, test extensively
public class Time implements Saveable
{
    //Creates Time object
    //Precondition: HOURS.set exists
    public Time(){
        loadFromFile("config/HOURS.set");
    }
    
    //Precondition: closingHour is between 0 and 24, and closingMinute is between 0 and 60
    //second constructor that accepts closingTime as input, if "HOURS.set" becomes lost
    public Time(int aOpeningHour, int aOpeningMinute, int aClosingHour, int aClosingMinute){
        openingHour = aOpeningHour;
        openingMinute = aOpeningMinute;
        closingHour = aClosingHour;
        closingMinute = aClosingMinute;
        saveToFile("config/HOURS.set");
    }
    
    //changes opening and closing hours, returns false if invalid values, or true if successful
    public boolean changeHours(String bOpeningHour, String bOpeningMinute, String bClosingHour, String bClosingMinute){
        try {
            int aOpeningHour = Integer.parseInt(bOpeningHour);
            int aOpeningMinute = Integer.parseInt(bOpeningMinute);
            int aClosingHour = Integer.parseInt(bClosingHour);
            int aClosingMinute = Integer.parseInt(bClosingMinute);
        
        if (aOpeningHour < 0 || aOpeningHour >= 24 || aClosingHour < 0 || aClosingHour >=24 || aOpeningMinute < 0 || aOpeningMinute >= 60
                || aClosingMinute < 0 || aClosingMinute >= 60 || aOpeningHour > aClosingHour){
            return false; //invalid values
        }
        openingHour = aOpeningHour;
        openingMinute = aOpeningMinute;
        closingHour = aClosingHour;
        closingMinute = aClosingMinute;
        saveToFile("config/HOURS.set");
        return true;
        }
        catch (NumberFormatException e){
            return false; //not a number, so invalid values
        }
    }
    
     //saves closing/opening times to file
    public boolean saveToFile(String filePath){
        try{
                PrintWriter out = new PrintWriter(filePath);
                out.println(openingHour);
                out.println(openingMinute);
                out.println(closingHour);
                out.println(closingMinute);
                out.close();
                return true;
            }
        catch (FileNotFoundException e){
                return false;
            }
    }
    
    //loads closing/opening times from file
    public boolean loadFromFile(String filePath){
        try{
            FileReader reader = new FileReader(filePath);
                Scanner in = new Scanner(reader);
                openingHour = in.nextInt();
                openingMinute = in.nextInt();
                closingHour = in.nextInt();
                closingMinute = in.nextInt();
                in.close();
                return true;
            }
        catch (FileNotFoundException e){
                return false;
            }
    }
    
    //returns true if store is open, or false if store is closed
    public boolean isAuthorized(){
        if (openingHour < getHour() && getHour() < closingHour) //if hour is between opening and closing hours
            return true;
        if (openingHour == getHour()){ //same as opening hour
            if (openingMinute < getMinute()) //if after opening minute
                return true;
        }
        if (closingHour == getHour()){ //same as  closing hour
            if (closingMinute > getMinute()) //if before closing minute
                return true;
        }
        return false; //if all else is false
    }
    
    //returns a string representation of the time, is static because object need not be constructed
    public static String getTime(){
        String min = "" + getMinute();
        if (getMinute() < 10) //makes 5:06 instead of 5:6
            min = "0" + min;
        return getHour() + ":" + min;
    }
    
    //returns a string representation of the date, in D/M/Y format, is static because object need not be constructed
    public static String getDate(){
        GregorianCalendar cal = new GregorianCalendar();
        String date = cal.get(Calendar.DAY_OF_MONTH) + "/" +  cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
        return date;
    }
    
    //returns current hour, is static because object need not be constructed
    public static int getHour(){
        GregorianCalendar cal = new GregorianCalendar();
        return cal.get(Calendar.HOUR_OF_DAY);
    }
    //returns current minute, is static because object need not be constructed
    public static int getMinute(){
        GregorianCalendar cal = new GregorianCalendar();
        return cal.get(Calendar.MINUTE);        
    }
    
    //4 accessor methods follow, all return fields they are named after
    public int getOpeningMinute(){
        return openingMinute;
    }
    
    public int getOpeningHour(){
        return openingHour;
    }
    
    public int getClosingHour(){
        return closingHour;
    }
    
    public int getClosingMinute(){
        return closingMinute;
    }
    
    private int openingHour;
    private int openingMinute;
    private int closingHour;
    private int closingMinute;
}
