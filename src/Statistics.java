import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
//keeps track of sales data, exports to spreadsheet
//TODO: implement saveable, save/load sales
public class Statistics implements Saveable{
        public Statistics(Inventory aInv){
            inv = aInv;
            isbn = new String[25];
            isbn = aInv.sort();
            sales = new int[25];
            for (int i = 0; i < 25; i++) {
                sales[i] = 0;
            }
            File filePath = new File("config/STATS.set");
            if (filePath.exists()){
                loadFromFile("config/STATS.set");
            }
        }
        
        //adds customer purchase data to statistics object
        public void addData(Customer c){
            totalMoney+= c.getTotalPrice();
            Book[] purchases = c.getInventory();
            for (Book b: purchases){
                if (b != null){
                    for (int i = 0; i < 25; i++){
                        if (b.getIsbn().equals(isbn[i])){
                            sales[i] += b.getNumCopies();
                        }
                    }
                }
            }
        }
        
        //saves sales data to .CSV spreadsheet that is specified, returns true if successful, false if failed
        public boolean exportCSV(String filePath){
        	try {
        		int count = 0; //count of items avaliable 
        		for (String l: isbn){
        			if (!l.equals(""))
        				count++;
        		}
                PrintWriter out = new PrintWriter(filePath);
                out.println("ISBN:, Book Name:, Sales:");
        		for (int i = 0; i < count; i++){
        			out.print(isbn[i]);
        			out.print(", ");
        			out.print(inv.getBook(isbn[i]).getTitle());
        			out.print(", ");
        			out.print(sales[i]);
        			out.println();
        		}
                out.println();
                out.println("Total sales:, $" + totalMoney);
                out.close();
                return true;
        	}
        	catch (FileNotFoundException e){
                return false;
            }
        }
        
        //true if successful, false if failed
        public boolean saveToFile(String filePath){
            try {
                PrintWriter out = new PrintWriter(filePath);
                int count = 0; //count of items avaliable 
        	for (String l: isbn){
        		if (!l.equals(""))
        			count++;
        	}
                out.println(count);
                out.println(totalMoney);
                for (int i = 0; i < count; i++){
                    out.println(isbn[i]);
                    out.println(sales[i]);
                }
                out.close();
                return true;
                
            }
            catch (FileNotFoundException e){
                return false;
            }
        }
        
        //true if load is successful, false if fails
        public boolean loadFromFile(String filePath){
            try {
                FileReader reader = new FileReader(filePath);
                Scanner in = new Scanner(reader);
                int count = Integer.parseInt(in.nextLine());
                totalMoney = Double.parseDouble(in.nextLine());
                for (int i = 0; i < count; i++){
                    isbn[i] = in.nextLine();
                    sales[i] = Integer.parseInt(in.nextLine());
                }                
                return true;
            }
            catch (FileNotFoundException e){
                return false;
            }
        }
        
        private Inventory inv;
        private String[] isbn;
        private int[] sales;
        private double totalMoney;
}