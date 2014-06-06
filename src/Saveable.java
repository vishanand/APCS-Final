//inteface to be used for all objects that save their contents to a file
public interface Saveable {
    
    //method to save object to a file, returns true if successful, or false if write failed
    public boolean saveToFile(String filePath);    
    
    
    //method to load object from file, returns true if successful, or false if filepath doesn't exist
    public boolean loadFromFile(String filePath);
}
