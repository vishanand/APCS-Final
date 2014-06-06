import java.io.FileNotFoundException;
 
public class inventoryTester 
{
    
    public static void main(String[] args) throws FileNotFoundException
    {
    	/**
    	 Tests the methods of the inventory class. 
    	 **/
    	Inventory inv = new Inventory();
        Customer b = new Customer(inv); //3 unused customers to check customer number
        Customer a = new Customer(inv);
        Customer s = new Customer(inv);
        Customer c = new Customer(inv);
        c.buyBook("123456", "3");
        c.buyBook("1234567891012", "2");
        b.buyBook("123456", "2");
        System.out.println(inv.getBook("123456").getTitle());
        System.out.println(c.getTotalPrice());
        for (int i = 0; i <25; i++)
            System.out.println(inv.sort()[i]); //tests sort() method
    }
}

