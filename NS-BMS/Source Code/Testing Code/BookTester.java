public class BookTester
{

	/**
	 This class tests all the methods of the book class. 
	**/
	public static void main(String[] args)
	{
		
		Book awesome = new Book(15.00, "Harry Potter", "485894048", "J.K. Rowling", 6 );
		awesome.getPrice();
		System.out.println(awesome.getPrice());
		awesome.getTitle();
		System.out.println(awesome.getTitle());
		awesome.getIsbn();
		System.out.println(awesome.getIsbn());
		awesome.getAuthor();
		System.out.println(awesome.getAuthor());
		awesome.getNumCopies();
		System.out.println(awesome.getNumCopies());
		awesome.stockBook(5);
		awesome.removeBook(8);
		
	     
	}
}