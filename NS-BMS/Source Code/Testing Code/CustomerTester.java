import java.io.FileNotFoundException;
 
public class CustomerTester 
{
    
    public static void main(String[] args) throws FileNotFoundException
    {
    	/**
    	 This class tests all the methods of the customer class. 
    	 **/
    	Inventory inv = new Inventory();
    	inv.addBook(new Book(5, "A tale of two cities", "123456", "Charles Dickens", 50));
    	inv.addBook(new Book(5, "A Christmas Carol", "1234567891012", "Charles Dickens", 50));
    	inv.updateFile();
        Customer b = new Customer(inv); //3 unused customers to check customer number
        Customer a = new Customer(inv);
        Customer s = new Customer(inv);
        Customer c = new Customer(inv);
        Statistics stats = new Statistics(inv);
        System.out.println(c.hasEnoughBooks("123456", "3"));
        c.buyBook("123456", "3");
        c.buyBook("1234567891012", "2");
        System.out.println(inv.getBook("123456").getTitle());
        System.out.println(c.getTotalPrice());
        inv.saveToFile("config/INVENTORY.set"); //saves file so changes are kept
        System.out.println(c.getBill(new Language("LNG1.set"))); //test this output in web browser, language needed for method
        stats.addData(c);
        stats.saveToFile("config/STATS.set");
    }
}

