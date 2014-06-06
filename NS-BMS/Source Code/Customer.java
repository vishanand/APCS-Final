
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer extends Inventory
{
	public Customer(Inventory aInv)
	{
            super(true); //calls constructor that doesn't load from file
            inv = aInv;
            customerNumL = customerNum;
            customerNum++; //next customer is new number
	}
	
	//returns true if there are enough, false if book doesn't exist or not enough, or if not a number quantity passed
	public boolean hasEnoughBooks(String wantedBookIsbn, String amountS)
	{
		try {
                    int amount = Integer.parseInt(amountS);
                    if(inv.getBook(wantedBookIsbn) != null && inv.getBook(wantedBookIsbn).getNumCopies() >= amount)
			return true;
		else
			return false;
                }
                catch (NumberFormatException e){
                    return false;
                }
	}
	//precondition: hasEnoughBooks() is true, client program should call hasEnoughBooks() before this
	public void buyBook(String isbn, String amountS)
	{
                if(hasEnoughBooks(isbn, amountS) != true)
                    throw new IllegalArgumentException("book doesn't exist, you violated precondition");		
                String[] isbns = this.sort();
                for (String i: isbns){
                    if (i != null && i.equals(isbn)){ //if book already bought, add to quantity
                        int amount = Integer.parseInt(amountS);
                        inv.getBook(isbn).removeBook(amount);
                        this.getBook(isbn).stockBook(amount);
                        return;
                    }
                }
                int amount = Integer.parseInt(amountS);
                inv.getBook(isbn).removeBook(amount);
                Book b = inv.getBook(isbn);
                this.addBook(new Book(b.getPrice(), b.getTitle(), b.getIsbn(), b.getAuthor(), amount)); //adds to inherited inventory array
	}
        
        //gets total price of all books combined
	public double getTotalPrice()
	{
		Book[] bookz = getInventory();
                double price = 0.0;
                for (Book b: bookz){
                    if (b != null){ //avoids null pointer exception
                        price += b.getPrice() * b.getNumCopies(); //adds price * number bought to price variable                        
                    }
                }
                int pr = (int) Math.round(price * 100); 
                price = pr / 100.0; //these two lines round to two places
                return price;
	}
        
        //returns bill formattted in HTML, adds it to bills.html
        public String getBill(Language lng){
            String bill = "<body style=\"text-align:center;\">\n" + "<h2>"+lng.getPhrase(Language.BILL_TITLE)+"</h2>\n";
            bill += "<h3>" + lng.getPhrase(Language.CUSTOMER) +customerNumL + " ";
            bill += Time.getTime() + " " + Time.getDate() + "</h3> <h4>";
            Book[] bookz = getInventory();
            for (Book b: bookz){
                if (b != null){ //avoids null pointer exception
                    bill += "<p>" + b.getTitle() + lng.getPhrase(Language.AUTHOR_LINKER) + b.getAuthor();
                    bill += "<br/> ISBN: " + b.getIsbn() + " $" + b.getPrice() + " Qnt: " +  b.getNumCopies() + "</p>";
                }
            }
            bill += "</h4> <h3> " + lng.getPhrase(Language.TOTAL_PRICE);
            bill += getTotalPrice();
            bill += "</h3></body>";
            try {  //appends bill to bills.html
                FileWriter out = new FileWriter("config/bills.html", true);
                out.write("</br>"+bill+"</br>");
                out.close();
            }
            catch (IOException e){
                try {
                    PrintWriter out = new PrintWriter("config/bills.html"); //if file doesn't exist, create it and write instead of append
                    out.println("</br>"+bill+"</br>");
                } catch (FileNotFoundException ex) { //never happens, but needed
                }
            }
            return bill;
        }
	
	private Inventory inv; //reference to inventory
        private final int customerNumL; //customer number
        private static int customerNum; //customer number last used
}