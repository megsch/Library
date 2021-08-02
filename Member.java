import java.util.*;

public class Member {
    
	private String name;
	private String memberNumber;
	private List<Book> rentHistory;
	private List<Book> currentlyRenting;
    
	public Member(String name, String memberNumber) {
    	this.name = name;
    	this.memberNumber = memberNumber;
    	this.rentHistory = new ArrayList<Book>();
    	this.currentlyRenting = new ArrayList<Book>();
	}
    
    
	public String getName() {
    	// Returns name of member
    	return this.name;
	}
    
    
	public String getMemberNumber() {
    	// Returns member number of the member
    	return this.memberNumber;
	}
    
	public List<Book> getCurrentlyRenting() {
    	return this.currentlyRenting;
	}
    
	public List<Book> getRentHistory() {
    	return this.rentHistory;
	}
    
    
	public boolean rent(Book book) {
    	// Rents given book
    	if (book == null) {
        	return false;
    	}
		
		if (book.getCurrentRenter() != null &&
   			 !book.getCurrentRenter().equals(this.getMemberNumber())) {
   		 return false;
    	} 
		if (this.currentlyRenting.contains(book)) {
			return false;
		} else {
        	this.currentlyRenting.add(book);
        	// book.setCurrentRenter(this.getMemberNumber());
        	return true;
    	}
	}
    
    
	public boolean relinquish(Book book) {
    	// Returns the book to the library
    	if (book == null) {
        	return false;
    	}
   	 
    	// Clear currently renting, and add to rentHistory
   	 
    	if (this.getCurrentlyRenting().contains(book)) {
        	this.currentlyRenting.remove(book);
        	this.rentHistory.add(book);
        	return true;
    	} else {
        	return false;
    	}
   	 
       	 
	}
    
    
    
    
	public void relinquishAll() {
    	// Returns all books rented by member
    	// Get list of currently rented from member
   	 
    	// Updates all histories and placeholders
    	for (Book book : this.getCurrentlyRenting()) {
        	book.addRenterToHistory(this);
        	book.setCurrentRenter(null);
        	this.rentHistory.add(book);
    	}
   	 
    	this.currentlyRenting.clear();
	}
    
    
	public List<Book> history() {
    	// Return the history of books rented, in order chronologically
    	return this.rentHistory;
	}
    
    
	public List<Book> renting() {
    	// Returns list of books currently being rented, in order chronologically
    	return this.currentlyRenting;
	}
    
    
	public static List<Book> commonBooks(Member[] members) {
    	// Returns shared books in members' renting histories, ordered by serial numbers
   	 
   	 if (members == null) {
   		 return null;
   	 }
   	 
   	 List<Book> sharedBooks = new ArrayList<Book>();
    	List<Member> membersList = new ArrayList<>(members.length);
   	 
    	// Check if all members are valid
    	for (int i = 0; i < members.length; i++) {
   		 if (members[i] != null) {
            	membersList.add(members[i]);
        	} else {
            	return null;
        	}
    	}
   	 
   	 
    	// Iterate through one member's history list, and compare them with other's
    	if (membersList.size() > 1) {
        	List<Book> booksToCompare = membersList.get(0).rentHistory;
        	for (Book bookCompare : booksToCompare) {    
            	for (int i = 1; i < membersList.size(); i++) {
                	for (Book book : membersList.get(i).rentHistory) {
                    	// Compare Serial number
               		 if (bookCompare.getSerialNumber().equals(book.getSerialNumber())) {
               			 sharedBooks.add(book);
               		 }
                	}
            	}
        	}
    	}
   	 
    	// Sort by serialNumber
    	sharedBooks = Book.sortBySerial(sharedBooks);
   	 
   	 
    	return sharedBooks;
	}
    
}
