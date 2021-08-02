import java.util.*;
import java.io.*;

public class Book {
    
	private String title;
	private String author;
	private String genre;
	private String serialNumber;
	private List<String> renterHistory;
	private String currentRenter;
    
	public Book(String title, String author, String genre, String serialNumber) {
    	this.title = title;
    	this.author = author;
    	this.genre = genre;
    	this.serialNumber = serialNumber;
    	this.renterHistory = new ArrayList<String>();
    	this.currentRenter = null;
	}
    
	public String getTitle() {
    	// Returns the title of the book
    	return this.title;
	}
    
    
	public String getAuthor() {
    	// Return the author of the book
    	return this.author;
	}
    
    
	public String getGenre() {
    	// Returns the genre of the book
    	return this.genre;
	}
    
    
	public String getSerialNumber() {
    	// Returns serial number of the book
    	return this.serialNumber;
	}
    
	public String getCurrentRenter() {
    	return this.currentRenter;
	}
    
	public void setCurrentRenter(String serialNumber) {
    	this.currentRenter = serialNumber;
	}
    
    
	public String longString() {
    	// Returns a long form of book's information
    	if (this.isRented()) {
        	String s = this.getSerialNumber() + ": " + this.getTitle() + " (" + this.getAuthor() +
                    	", " + this.getGenre() + ")\nRented by: " + this.currentRenter + ".";
        	return s;
    	} else {
        	String s = this.getSerialNumber() + ": " + this.getTitle() + " (" + this.getAuthor() +
                    	", " + this.getGenre() + ")\nCurrently available.";
        	return s;
    	}

	}
    
    
	public String shortString() {
    	// Returns a short form of book's information
    	String shortStr = this.getTitle() + " (" + this.getAuthor() + ")";
    	return shortStr;
	}
    
    
	public List<String> renterHistory() {
    	// Returns the renter history
    	return this.renterHistory;
	}
    
    
	public boolean isRented() {
    	// Returns if the book is currently unavailable
    	if (this.currentRenter == null) {
        	return false;
    	} else {
        	return true;
    	}
	}
    
    
	public boolean rent(Member member) {
    	// Sets the current renter to the given member
    	if (member == null || this.currentRenter != null) {
        	return false;
    	} else {
        	this.currentRenter = member.getMemberNumber();
        	return true;
    	}
	}
    
    
	public void addRenterToHistory(Member member) {
    	// Add renter to the renter history
    	this.renterHistory.add(member.getMemberNumber());
	}
    
    
	public boolean relinquish(Member member) {
    	// Returns to library
    	// Make book available, add renter to renter history
    	if (member == null) {
        	return false;
    	}
   	 
    	// Check if member is renting book
    	if (member.getMemberNumber().equals(this.currentRenter)) {
        	this.currentRenter = null;
        	this.renterHistory.add(member.getMemberNumber());
        	return true;
    	} else {
        	return false;
    	}
   	 
	}
    
    
	public static Book readBook(String filename, String serialNumber) {
    	// Retrieves the book from the file based on its serial number
   	 
    	if (filename == null || serialNumber == null) {
        	return null;
    	}
   	 
    	File f = new File(filename);
    	Book foundBook = null;
   	 
    	try {
        	Scanner scan = new Scanner(f);
        	scan.nextLine();
        	while (scan.hasNextLine()) {
            	// Read through file, and find book with right serial number
            	String[] line = scan.nextLine().split(",");
            	if (line[0].contentEquals(serialNumber)) {
                	// Make book object
                	foundBook = new Book(line[1], line[2], line[3], line[0]);
                	break;
            	}
        	}
        	scan.close();
    	} catch (FileNotFoundException e) {
        	return null;
    	}
   	 
    	return foundBook;
	}
    
    
	public static List<Book> readBookCollection(String filename) {
    	// Reads in the collection of books from the file
   	 
    	if (filename == null) {
        	return null;
    	}
   	 
    	List<Book> bookList = new ArrayList<Book>();
    	File f = new File(filename);
   	 
    	try {
        	Scanner scan = new Scanner(f);
        	// Go through books in file, add to bookList
        	while (scan.hasNextLine()) {
            	String[] line = scan.nextLine().split(",");
            	if (line[0].equals("serialNumber")) {
                	continue;
            	}
            	Book book = new Book(line[1], line[2], line[3], line[0]);
            	bookList.add(book);
        	}
        	scan.close();
    	} catch (FileNotFoundException e) {
        	return null;
    	}
   	 
    	return bookList;
	}
    
    
	public static void saveBookCollection(String filename, Collection<Book> books) {
    	// Save the collection of books to the file
   	 
    	if (filename == null || books == null) {
        	return;
    	}
    	if (books.size() == 0) {
        	return;
    	}
   	 
    	File f = new File(filename);
    	Book[] booksArray = new Book[books.size()];
    	booksArray = books.toArray(booksArray);
   	 
    	// Iterate through Collection of books, add them to file
    	PrintWriter writer = null;
    	try {
        	writer = new PrintWriter(f);
    	} catch (FileNotFoundException e) {
        	return;
    	}
   	 
    	writer.println("serialNumber,title,author,genre");
    	for (int i = 0; i < booksArray.length; i++) {
      	String line = booksArray[i].getSerialNumber() + "," + booksArray[i].getTitle() +
              	"," + booksArray[i].getAuthor() + "," + booksArray[i].getGenre();
      	writer.print(line);
      	if (i < booksArray.length - 1) {
          	writer.println();
      	}
    	}
    	writer.close();
    
	}
    
    
	public static List<Book> filterAuthor(List<Book> books, String author) {
    	// Creates a list containing books by the author
    	if (books == null || author == null) {
        	return null;
    	}
   	 
    	List<Book> booksAuthor = new ArrayList<>();
    	// Iterate through books
    	for (Book book : books) {
        	if (book.getAuthor().equals(author)) {
            	booksAuthor.add(book);
        	}
    	}
    	booksAuthor = Book.sortBySerial(booksAuthor);
    	return booksAuthor;
	}
    
    
	public static List<Book> filterGenre(List<Book> books, String genre) {
    	// Creates a list containing books by the genre
    	if (books == null || genre == null) {
        	return null;
    	}
   	 
    	List<Book> booksGenre = new ArrayList<>();
    	// Iterate through books
    	for (Book book : books) {
        	if (book.getGenre().equals(genre)) {
            	booksGenre.add(book);
        	}
    	}
    	booksGenre = Book.sortBySerial(booksGenre);
    	return booksGenre;
	}
    
	public static List<Book> sortBySerial(List<Book> books) {
    	for (int i = 0; i < books.size(); i++) {
        	for (int j = i+1; j < books.size(); j++) {
            	if (books.get(i).getSerialNumber().compareTo(books.get(j).getSerialNumber()) > 0) {
                	// Swap books
                	Book temp = books.get(i);
                	books.add(i, books.get(j));
                	books.add(j, temp);
            	}
        	}
    	}
    	return books;
	}

}
