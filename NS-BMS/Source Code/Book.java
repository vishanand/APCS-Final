/**
 * @(#)Book.java
 *
 * Book application
 *
 * @author 
 * @version 1.00 2014/5/13
 */
 

public class Book 
{
	/**
	 * Constructs a book with a price, a title, an ISBN number and an author. 
	 */
    public Book(double price,String title, String isbnNumber, String author, int copies)
    {
    	bookPrice = price;
    	bookTitle = title;
    	bookNumber = isbnNumber;
    	bookAuthor = author;
    	numCopies = copies;
    }
    /**
     * returns the price of the constructed book. 
     */
    public double getPrice()
    {
    	return bookPrice;
    }
    /**
     * returns the title of the constructed book. 
     */
    public String getTitle()
    {
    	return bookTitle;
    }
    /**
     * returns the ISBN number of the constructed book. 
     */
    public String getIsbn()
    {
    	return bookNumber;
    }
    /**
     * returns the author of the constructed book. 
     */
    public String getAuthor()
    {
    	return bookAuthor; 
    }
  	//returns the amount of copies availiable
  	public int getNumCopies(){
            return numCopies;
  	}
        //stocks a book
  	public void stockBook(int copies){
  		numCopies += copies;
  	}
  	//precondition: there are enough books to remove
  	public void removeBook(int copies){
            if (copies > numCopies){
                throw new IllegalArgumentException("Not enough copies");
            }
            else {
                numCopies -= copies;
            }
  	}
    
    private double bookPrice;
    private String bookTitle;
    private String bookNumber;
    private String bookAuthor;
    private int numCopies;
}