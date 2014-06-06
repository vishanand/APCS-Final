
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

//TODO: add sort() method to sort by ISBN number
//this class stores the books in an array, should call update() to save
public class Inventory implements Saveable{

        //creates a blank inventory class, loads from file if possible
	public Inventory(){
            inventory = new Book[25];
            File filePath = new File("config/INVENTORY.set");
            if (filePath.exists()){
                loadFromFile("config/INVENTORY.set");
            }
	}
        
        //creates new book from string values, returns null if incorrect parameters
        public static Book createBook(String price,String title, String isbnNumber, String author, String copies)
        {
            try{
                double bookPrice = Double.parseDouble(price);
                String bookTitle = title;
                String bookNumber = isbnNumber;
                String bookAuthor = author;
                int numCopies = Integer.parseInt(copies);            
                return new Book(bookPrice, bookTitle, bookNumber, bookAuthor, numCopies);
            }
            catch(NumberFormatException e){
                return null;
            }
        }
        //gets sorted array of ISBN numbers
        public String[] sort(){
            String[] isbnNums = new String[25];
            for (int i = 0; i<25; i++){ //fills array with isbn numbers
                if (inventory[i] != null)                   
                    isbnNums[i] = inventory[i].getIsbn();                
                else
                    isbnNums[i] = ""; //blank
            }
            Arrays.sort(isbnNums); //uses java sort, ours couldn't properly sort strings
            int firstReal = 0; //following code places all null isbns at the end
            for (int i = 0; i<25; i++){
                if (!isbnNums[i].equals("")){
                    firstReal = i;
                    break;
                }
            }
            String newIsbnNums[] = new String[25];
            for (int i = 0; i < 25; i++){
                newIsbnNums[i] = "";
            }
            int temp = 0;
            for (int i = firstReal; i < 25; i++){
                newIsbnNums[temp] = isbnNums[i];
                temp++;
            }
            return newIsbnNums;
        }
        
        //creates blank inventory, doesn't load from file
        public Inventory(boolean b){
            inventory = new Book[25];
        }
        
        //precondition: there are less than 25 books existing
        //adds a new unique book to the inventory, returns true if successful, returns false if isbn occupied or too many books left
        public boolean addBook(Book b){
            for (Book book: inventory){ //tests if ISBN is already occupied
                if (book != null && b.getIsbn().equals(book.getIsbn())){
                    return false;
                }
            }
            for (int i = 0; i<25; i++){
                if (inventory[i] == null){
                    inventory[i] = b;
                    return true; //creates book and returns true to signal success
                }
            }
            return false; //if no blank spaces for books
        }
        
        //precondition: the isbn corresponds to an existing book
        //removes a book from the inventory
        public void removeBook(String isbn){
            for (int i = 0; i<25; i++){
                if (inventory[i] != null && isbn.equals(inventory[i].getIsbn())){ //prevents null pointer exception
                    inventory[i] = null;
                    return;
                }
            }
        }
	
        //precondition: The isbn corresponds to an existing book
        //returns book based on isbn, returns null if isbn doesn't correspond to book
        public Book getBook(String isbn){
            for (Book b: inventory){
                if (b != null && isbn.equals(b.getIsbn())){
                    return b;
                }
            }
            return null;
        }
    
    //saves inventory to file
    public void updateFile(){
        saveToFile("config/INVENTORY.set");
    }
    
    //returns amount of unique non null books in inventory
    public int countItems(){
        int numItems = 0;
        for (Book b: inventory){
                    if (b != null)
                        numItems ++;
                }
        return numItems;
    }
    //saves inventory to file    
    public boolean saveToFile(String filePath){
        try {
                PrintWriter out = new PrintWriter(filePath);
                int numItems = countItems();
                out.println(numItems); //saves number of books to file, to help with loadFromFile() method
                for (int i = 0; i<numItems; i++){ //saves all fields of each book to file
                    out.println(inventory[i].getPrice());        
                    out.println(inventory[i].getTitle());        
                    out.println(inventory[i].getIsbn());        
                    out.println(inventory[i].getAuthor());        
                    out.println(inventory[i].getNumCopies());                  
                }
                out.close();
                return true;
            }
        catch (FileNotFoundException e){
                return false;
            }
    }
    
    //returns inventory array (used by customer class rather than making inventory field public
    public Book[] getInventory(){
        return inventory;
    }
    
    //loads inventory from file
    //precondition: file exists and has not been modified outside of this program
    public boolean loadFromFile(String filePath){
        try{
            FileReader reader = new FileReader(filePath);
                Scanner in = new Scanner(reader);
                int numItems = Integer.parseInt(in.nextLine()); //always reads line then parses, because error otherwise
                for (int i = 0; i<numItems; i++){ //loads all values to inventory array
                    String price = in.nextLine();
                    String title = in.nextLine();
                    String isbn = in.nextLine();
                    String author = in.nextLine();
                    String copies = in.nextLine();
                    inventory[i] = new Book(Double.parseDouble(price), title, isbn, author, Integer.parseInt(copies));
                }
                in.close();
                return true;
            }
        catch (FileNotFoundException e){
                return false;
            }
    }
	
	private Book[] inventory;
}
