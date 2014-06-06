//tests the language class
public class languageTester {

    public static void main(String[] args) {
        Language english = new Language("lng2.set"); //creates language object
        System.out.println(english.getPhrase(Language.BEGIN_SHOPPING_IN_LANGUAGE));
        System.out.println(english.loadFromFile("fakefile")); //should print false, because file doesn't exist
    }
    
}
