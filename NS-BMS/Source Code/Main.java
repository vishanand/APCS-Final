import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//this class is the GUI, there is only one gui class, because we use a cardlayout
public class Main {
    public static void main(String[] args) {
        final Language lng1 = new Language("LNG1.set");
        final Language lng2 = new Language("LNG2.set");
        final JFrame myFrame = new JFrame();
        final JPanel cards = new JPanel(new CardLayout());
        final Password pass = new Password(); //creates objects of the core classes
        final Inventory inv = new Inventory();
        final Statistics stats = new Statistics(inv);
        final Time time = new Time();        
        final JLabel isbnLabel = new JLabel();
        final JTextField isbn = new JTextField(15);
        final JLabel qLabel = new JLabel();
        final JTextField quantity = new JTextField(5);
        final JButton scanBarcode = new JButton();
        final JButton finishShopping = new JButton();
        final JButton exitBill = new JButton();
        final JLabel billLabel = new JLabel();
        final JLabel customerLabel = new JLabel();
        final JButton customerBuy[] = new JButton[25];
        final JButton itemEdit[] = new JButton[25];
        final JButton itemDelete[] = new JButton[25];
        
        class cardListener implements ActionListener{
        private Customer currentCustomer = new Customer(inv);
        private Language lng;
            public void actionPerformed(ActionEvent event){
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                String cmd = event.getActionCommand();
                    if (cmd.equals("Settings")){
                    	 String pwd = showInputDialog("Enter Password: ");
                         if (pwd != null && pass.checkPassword(pwd))
                            cardLayout.show(cards, "Settings"); //go to settings card
                         else
                            showMessageDialog(myFrame, "Password Incorrect!");                        
                    }
                    if (cmd.equals("gohome")){
                    	cardLayout.show(cards, "Main"); //go to main card
                        redrawInventory();
                    }
                    if (cmd.equals("changepass")){
                    	JPanel changePassPanel = new JPanel(new GridLayout(4,1));
                        JTextField oldPass = new JTextField();
                        JTextField newPass = new JTextField();
                        changePassPanel.add(new JLabel("Enter New Password:"));
                        changePassPanel.add(newPass);
                        changePassPanel.add(new JLabel("Enter Original Password:"));
                        changePassPanel.add(oldPass);
                        JOptionPane.showMessageDialog(null, changePassPanel);
                        boolean isSuccess = pass.changePassword(newPass.getText(), oldPass.getText());
                        if (isSuccess)
                            showMessageDialog(myFrame, "Password Successfully Changed!");
                        else
                            showMessageDialog(myFrame, "Password not changed, incorrect original password entered!");
                    }
                    if (cmd.equals("exportstats")){
                    	JFileChooser saveStats = new JFileChooser();
                        int returnValue = saveStats.showSaveDialog(myFrame);
                        if (returnValue == JFileChooser.APPROVE_OPTION)
                            stats.exportCSV(saveStats.getSelectedFile().getPath() + ".csv");
                    }
                    if (cmd.equals("editstorehours")){
                    	JTextField openHour = new JTextField();
                        JTextField openMinute = new JTextField();
                        JTextField closeHour = new JTextField();
                        JTextField closeMinute = new JTextField();
                        JPanel editStore = new JPanel(new GridLayout(5,2));
                        editStore.add(new JLabel("Enter new store hours (24 hour clock): "));
                        editStore.add(new JLabel());
                        editStore.add(new JLabel("Opening Hour: "));
                        editStore.add(openHour);
                        editStore.add(new JLabel("Opening Minute: "));
                        editStore.add(openMinute);                        
                        editStore.add(new JLabel("Closing Hour: "));
                        editStore.add(closeHour);                        
                        editStore.add(new JLabel("Closing Minute: "));
                        editStore.add(closeMinute);
                        openMinute.setText("" +time.getOpeningMinute());
                        openHour.setText("" +time.getOpeningHour());
                        closeHour.setText("" +time.getClosingHour());
                        closeMinute.setText("" +time.getClosingMinute());
                        JOptionPane.showMessageDialog(null, editStore);
                        boolean isSuccessful = time.changeHours(openHour.getText(), openMinute.getText(),
                                closeHour.getText(), closeMinute.getText());
                        if (isSuccessful)
                            showMessageDialog(myFrame, "Store hours changed successfully!");
                        else
                            showMessageDialog(myFrame, "Invalid values entered, store hours not changed!");
                    }
                    if (cmd.equals("ShopLng1")){
                        redrawLanguage(lng1);
                        redrawInventory();
                    	if (!time.isAuthorized())
                            showMessageDialog(myFrame, lng.getPhrase(Language.STORE_CLOSED));
                        else{
                            currentCustomer = new Customer(inv);
                            quantity.setText("1"); //resets quantity field to avoid annoying customer
                            cardLayout.show(cards, "shop"); //go to shopping card                        	
                        }
                    }
                    if (cmd.equals("ShopLng2")){
                        redrawLanguage(lng2);
                        redrawInventory();
                    	if (!time.isAuthorized())
                            showMessageDialog(myFrame, lng.getPhrase(Language.STORE_CLOSED));
                        else{
                            currentCustomer = new Customer(inv);
                            quantity.setText("1"); //resets quantity field to avoid annoying customer
                            cardLayout.show(cards, "shop"); //go to shopping card                        	
                        }
                    }
                    if (cmd.equals("backtoconfig")){
                    	cardLayout.show(cards, "Settings"); //go to settings card
                    }
                    if (cmd.equals("editinventory")){
                        lng = lng1;
                        redrawInventory();
                    	if (!time.isAuthorized())
                            showMessageDialog(myFrame, "Store is now closed, inventory editing not authorized!");
                        else
                            cardLayout.show(cards, "inventoryeditor"); //go to shopping card
                    }
                    if (cmd.equals("scanBarcode")){
                        if (currentCustomer.hasEnoughBooks(isbn.getText(), quantity.getText())){
                            currentCustomer.buyBook(isbn.getText(), quantity.getText());
                            showMessageDialog(myFrame, lng.getPhrase(Language.ITEM_ADDED_TO_CART));
                        }
                        else{
                            showMessageDialog(myFrame, lng.getPhrase(Language.ISBN_SCAN_ERROR));
                        }
                    }
                    if (cmd.equals("finishshopping")){
                    	cardLayout.show(cards, "bill");
                    	stats.addData(currentCustomer);
                    	billLabel.setText("<html>"+currentCustomer.getBill(lng)+"</html>");
                    }
                    if (cmd.equals("exitbill")){
                    	cardLayout.show(cards, "Main");
                    }
                    if (cmd.substring(0, 3).equals("buy")){
                        String isbn = inv.sort()[Integer.parseInt(cmd.substring(3))];
                        if (currentCustomer.hasEnoughBooks(isbn, quantity.getText())){                            
                            currentCustomer.buyBook(isbn, quantity.getText());
                            showMessageDialog(myFrame, lng.getPhrase(Language.ITEM_ADDED_TO_CART));
                        }
                        else{
                            showMessageDialog(myFrame, lng.getPhrase(Language.INSUFFICIENT_QUANTITY));
                        }
                    }
                    if (cmd.equals("addbook")){
                        JPanel editItemPanel = new JPanel(new GridLayout(5,2));
                            JTextField Isbn = new JTextField();                           
                            JTextField title = new JTextField();
                            JTextField author = new JTextField();
                            JTextField price = new JTextField();
                            JTextField stock = new JTextField();
                            editItemPanel.add(new JLabel("Enter ISBN:"));
                            editItemPanel.add(Isbn);
                            editItemPanel.add(new JLabel("Enter Title:"));
                            editItemPanel.add(title);
                            editItemPanel.add(new JLabel("Enter Author:"));
                            editItemPanel.add(author);
                            editItemPanel.add(new JLabel("Enter Price:"));
                            editItemPanel.add(price);
                            editItemPanel.add(new JLabel("Enter Stock:"));
                            editItemPanel.add(stock);
                            JOptionPane.showMessageDialog(null, editItemPanel);
                            Book b = inv.createBook(price.getText(), title.getText(), Isbn.getText(), author.getText(), stock.getText());
                            if (b != null){
                                if (inv.addBook(b)){
                                    showMessageDialog(myFrame, "Book Successfully Added to inventory!");                                    
                                }
                                else{                                    
                                    if (inv.countItems()<25)
                                        showMessageDialog(myFrame, "Book not added to inventory, ISBN already occupied!");
                                    else //more than 25 items
                                        showMessageDialog(myFrame, "Book not added to inventory, maximum 25 books allowed in inventory!");
                                }
                            }
                            else
                                showMessageDialog(myFrame, "Book not added to inventory, invalid parameters entered!");
                            redrawInventory();
                    }
                    if (cmd.substring(0, 3).equals("del")){
                        String isbn = inv.sort()[Integer.parseInt(cmd.substring(3))];
                        if (!isbn.equals("")){   
                            int choice = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to remove this book from the inventory?");
                            if (choice == JOptionPane.YES_OPTION){
                                inv.removeBook(isbn);
                                showMessageDialog(myFrame, "Book removed from inventory!");                                
                            }
                        }
                        else{
                            showMessageDialog(myFrame, "Book doesn't exist!");
                        }
                        redrawInventory();
                    }
                    if (cmd.substring(0, 4).equals("Edit")){
                        String isbn = inv.sort()[Integer.parseInt(cmd.substring(4))];
                        if (!isbn.equals("")){                            
                            JPanel editItemPanel = new JPanel(new GridLayout(5,2));
                            JTextField Isbn = new JTextField();
                            Isbn.setText(inv.getBook(isbn).getIsbn());
                            JTextField title = new JTextField();
                            title.setText(inv.getBook(isbn).getTitle());
                            JTextField author = new JTextField();
                            author.setText(inv.getBook(isbn).getAuthor());
                            JTextField price = new JTextField();
                            price.setText(inv.getBook(isbn).getPrice()+"");
                            JTextField stock = new JTextField();
                            stock.setText(inv.getBook(isbn).getNumCopies()+"");
                            editItemPanel.add(new JLabel("Enter ISBN:"));
                            editItemPanel.add(Isbn);
                            editItemPanel.add(new JLabel("Enter Title:"));
                            editItemPanel.add(title);
                            editItemPanel.add(new JLabel("Enter Author:"));
                            editItemPanel.add(author);
                            editItemPanel.add(new JLabel("Enter Price:"));
                            editItemPanel.add(price);
                            editItemPanel.add(new JLabel("Enter Stock:"));
                            editItemPanel.add(stock);
                            JOptionPane.showMessageDialog(null, editItemPanel);
                            Book b = inv.createBook(price.getText(), title.getText(), Isbn.getText(), author.getText(), stock.getText());
                            if (b != null){
                                inv.removeBook(isbn); //edits by adding then removing the book
                                inv.addBook(b);
                                showMessageDialog(myFrame, "Book Successfully Edited!");
                            }
                            else
                                showMessageDialog(myFrame, "Book not edited, invalid parameters entered!");
                        }
                        else{
                            showMessageDialog(myFrame, "Book doesn't exist, cannot be edited,\n use Add new book button instead!");
                        }
                        redrawInventory();
                    }
                
            }
            
            //method redraws inventory on both the customer purchase card and the inventory editor card
            public void redrawInventory(){
                String isbns[] = inv.sort();
                for (int i = 0; i<25; i++){
                    Book b = inv.getBook(isbns[i]);
                    String book = "";
                    if (b != null){
                        book = lng.getPhrase(Language.CURRENCY_SIGN) +b.getPrice() + " " + b.getTitle();
                        book += lng.getPhrase(Language.AUTHOR_LINKER) + b.getAuthor() + " ISBN: " + b.getIsbn();
                    }
                    customerBuy[i].setText(book);
                    if (b!=null)
                        book += " Click to edit!";
                    itemEdit[i].setText(book);
                }
                inv.updateFile();
                stats.saveToFile("config/STATS.set");
            }
            
            //method redraws the customer card to be in the language given
            public void redrawLanguage(Language lan){
                lng = lan; //sets bill language to this
                customerLabel.setText(lng.getPhrase(Language.SCAN_OR_CHOOSE));
                isbnLabel.setText(lng.getPhrase(Language.SCAN_BARCODE));
                scanBarcode.setText(lng.getPhrase(Language.SCAN));
                qLabel.setText(lng.getPhrase(Language.ENTER_NUM_BOOKS));
                finishShopping.setText(lng.getPhrase(Language.END_SHOPPING));
                exitBill.setText(lng.getPhrase(Language.LEAVE_BILL));
            }           
        }
        ActionListener listener = new cardListener();
        
        //card for Main screen
        final JButton goShopLng1 = new JButton(lng1.getPhrase(Language.BEGIN_SHOPPING_IN_LANGUAGE)); //start shopping button, english
        Font settingsFont = new Font("Serif", Font.PLAIN, 28);
        goShopLng1.setActionCommand("ShopLng1");
        goShopLng1.addActionListener(listener);
        goShopLng1.setFont(settingsFont);
        final JButton goShopLng2 = new JButton(lng2.getPhrase(Language.BEGIN_SHOPPING_IN_LANGUAGE)); //start shopping button, spanish
        goShopLng2.setActionCommand("ShopLng2");
        goShopLng2.addActionListener(listener);
        goShopLng2.setFont(settingsFont);
        final JButton goToSettings = new JButton("Manager Configuration!"); //goto settings button
        goToSettings.setActionCommand("Settings");
        goToSettings.addActionListener(listener);
        goToSettings.setFont(new Font("Serif", Font.PLAIN, 22));
        final JPanel shoppingPanel = new JPanel(new GridLayout(1,2));
        shoppingPanel.add(goShopLng1);
        shoppingPanel.add(goShopLng2);
        final JPanel goToSettingsPanel = new JPanel(new GridLayout(1,3));
        goToSettingsPanel.add(goToSettings);
        goToSettingsPanel.add(new JLabel()); //empty blanks
        goToSettingsPanel.add(new JLabel());
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(shoppingPanel, BorderLayout.CENTER);
        mainPanel.add(goToSettingsPanel, BorderLayout.SOUTH);
        ImageIcon webbazon = new ImageIcon("Webbazon.png");
        JLabel welcomeToWebbazon = new JLabel();
        welcomeToWebbazon.setIcon(webbazon);
        mainPanel.add(welcomeToWebbazon, BorderLayout.NORTH);
        cards.add(mainPanel, "Main");
        
        //card for settings
        final JPanel settingsPanel = new JPanel(new GridLayout(6,1));
        JLabel settingsLabel = new JLabel();
        settingsLabel.setIcon(new ImageIcon("Settings.png"));
        settingsPanel.add(settingsLabel);
        final JButton changePass = new JButton("Change Password!");
        changePass.setFont(settingsFont);
        changePass.addActionListener(listener);
        changePass.setActionCommand("changepass");
        settingsPanel.add(changePass);
        final JButton editStoreHours = new JButton("Edit Store Hours!");
        editStoreHours.setFont(settingsFont);
        editStoreHours.addActionListener(listener);
        editStoreHours.setActionCommand("editstorehours");
        settingsPanel.add(editStoreHours);
        final JButton editInventory = new JButton("Edit Inventory!");
        editInventory.setFont(settingsFont);
        editInventory.addActionListener(listener);
        editInventory.setActionCommand("editinventory");
        settingsPanel.add(editInventory);
        final JButton exportStats = new JButton("Export Statistics to spreadsheet!");
        exportStats.setFont(settingsFont);
        exportStats.addActionListener(listener);
        exportStats.setActionCommand("exportstats");
        settingsPanel.add(exportStats);
        final JButton goHomeSettings = new JButton("Exit Configuration!");
        goHomeSettings.setFont(settingsFont);
        goHomeSettings.addActionListener(listener);
        goHomeSettings.setActionCommand("gohome");
        settingsPanel.add(goHomeSettings);
        cards.add(settingsPanel, "Settings");
        
        //card for inventory editor
        final JPanel invEditPanel = new JPanel(new BorderLayout());
        JLabel invEditTitle = new JLabel();
        invEditTitle.setIcon(new ImageIcon("Inventory.png"));
        invEditPanel.add(invEditTitle, BorderLayout.NORTH);
        final JButton backToConfig = new JButton("Back to configuration!");
        backToConfig.addActionListener(listener);
        backToConfig.setActionCommand("backtoconfig");
        JPanel bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.add(backToConfig);
        final JButton addBook = new JButton("Add new book to inventory!");
        addBook.addActionListener(listener);
        addBook.setActionCommand("addbook");
        bottomPanel.add(addBook);
        invEditPanel.add(bottomPanel, BorderLayout.SOUTH);
        JPanel centrePanel = new JPanel(new BorderLayout());
        JPanel centreButtons = new JPanel(new GridLayout(25,1));
        JPanel rightButtons = new JPanel(new GridLayout(25,1));
        String isbns[] = inv.sort();
        for (int i = 0; i<25; i++){
            Book b = inv.getBook(isbns[i]);
            String book = "";
            if (b != null){
                book = "$" +b.getPrice() + " " + b.getTitle();
                book += " by " + b.getAuthor() + " ISBN: " + b.getIsbn();
                book += " Click to edit!";
            }
            itemEdit[i] = new JButton(book);
            itemEdit[i].addActionListener(listener);
            itemEdit[i].setActionCommand("Edit"+i);
            centreButtons.add(itemEdit[i]);
            itemDelete[i] = new JButton("Delete!");
            itemDelete[i].addActionListener(listener);
            itemDelete[i].setActionCommand("del"+i);
            rightButtons.add(itemDelete[i]);
        }
        centrePanel.add(centreButtons, BorderLayout.CENTER);
        centrePanel.add(rightButtons, BorderLayout.EAST);
        JScrollPane centreScrollPane = new JScrollPane(centrePanel,JScrollPane
                .VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        invEditPanel.add(centreScrollPane, BorderLayout.CENTER);
        cards.add(invEditPanel, "inventoryeditor");
        
        //card for customer purchases
        final JPanel customerPanel = new JPanel(new BorderLayout());
        Font titleFont = new Font("Serif", Font.BOLD, 28);
        customerLabel.setFont(titleFont);
        customerLabel.setHorizontalAlignment(SwingConstants.CENTER);        
        customerPanel.add(customerLabel, BorderLayout.NORTH);
        final JPanel leftPanel = new JPanel(new GridLayout(7,1)); //I accidently mixed up left and right for the panel names      
        scanBarcode.addActionListener(listener);
        scanBarcode.setActionCommand("scanBarcode");        
        quantity.setText("1");
        JPanel isbnPanel = new JPanel();
        isbnPanel.add(isbn);        
        isbnPanel.add(scanBarcode);
        JPanel qPanel = new JPanel();
        JPanel scanPanel = new JPanel(new GridLayout(2,1));
        scanPanel.add(isbnLabel);
        scanPanel.add(isbnPanel);
        leftPanel.add(scanPanel);
        qPanel.add(qLabel);
        qPanel.add(quantity);
        leftPanel.add(qPanel);        
        finishShopping.addActionListener(listener);
        finishShopping.setActionCommand("finishshopping");
        leftPanel.add(new JLabel());
        leftPanel.add(new JLabel());
        leftPanel.add(new JLabel());
        leftPanel.add(new JLabel());
        leftPanel.add(finishShopping);
        customerPanel.add(leftPanel, BorderLayout.EAST);
        final JPanel rightPanel = new JPanel(new GridLayout(25,1));
        for (int i = 0; i<25; i++){
            Book b = inv.getBook(isbns[i]);
            String book = "";
            if (b != null){
                book = "$" +b.getPrice() + " " + b.getTitle();
                book += " by " + b.getAuthor() + " ISBN: " + b.getIsbn();
            }
            customerBuy[i] = new JButton(book);
            customerBuy[i].addActionListener(listener);
            customerBuy[i].setActionCommand("buy"+i);
            rightPanel.add(customerBuy[i]);
        }
        JScrollPane rightPane = new JScrollPane(rightPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        customerPanel.add(rightPane, BorderLayout.WEST);
        cards.add(customerPanel, "shop");
        
        //card for bill
        final JPanel billPanel = new JPanel(new BorderLayout());
        billLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane billPane = new JScrollPane(billLabel);
        billPanel.add(billPane, BorderLayout.CENTER);
        exitBill.addActionListener(listener);
        exitBill.setActionCommand("exitbill");
        billPanel.add(exitBill, BorderLayout.SOUTH);
        cards.add(billPanel, "bill");
        ((cardListener)listener).redrawLanguage(lng1);
               
        
        myFrame.add(cards);
        myFrame.setSize(800, 600);
        myFrame.setResizable(false); //shouldn't be resizeable because gui set up in 800X600
        myFrame.setTitle("NeptuneSoft Bookstore Management System");        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setIconImage(new ImageIcon("Logo.png").getImage());
        myFrame.setVisible(true);
       
        
    

    }
}
    