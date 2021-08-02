import java.util.*;
import java.io.*;

public class Library {
    
	public static final String HELP_STRING = "EXIT ends the library process\nCOMMANDS outputs this "
        	+ "help string\n\nLIST ALL [LONG] outputs either the short or long string for all books"
        	+ "\nLIST AVAILABLE [LONG] outputs either the short of long string for all available"
        	+ " books\nNUMBER COPIES outputs the number of copies of each book\nLIST GENRES outputs"
        	+ " the name of every genre in the system\nLIST AUTHORS outputs the name of every"
        	+ " author in the system\n\nGENRE <genre> outputs the short string of every book with "
        	+ "the specified genre\nAUTHOR <author> outputs the short string of every book by the "
        	+ "specified author\n\nBOOK <serialNumber> [LONG] outputs either the short or long "
        	+ "string for the specified book\nBOOK HISTORY <serialNumber> outputs the rental history"
        	+ " of the specified book\n\nMEMBER <memberNumber> outputs the information of the "
        	+ "specified member\nMEMBER BOOKS <memberNumber> outputs the books currently rented by "
        	+ "the specified member\nMEMBER HISTORY <memberNumber> outputs the rental history of "
        	+ "the specified member\n\nRENT <memberNumber> <serialNumber> loans out the specified "
        	+ "book to the given member\nRELINQUISH <memberNumber> <serialNumber> returns the "
        	+ "specified book from the member\nRELINQUISH ALL <memberNumber> returns all books "
        	+ "rented by the specified member\n\nADD MEMBER <name> adds a member to the system\nADD"
        	+ " BOOK <filename> <serialNumber> adds a book to the system\n\nADD COLLECTION "
        	+ "<filename> adds a collection of books to the system\nSAVE COLLECTION <filename> "
        	+ "saves the system to a csv file\n\nCOMMON <memberNumber1> <memberNumber2> ... outputs"
        	+ " the common books in members\' history";

	private ArrayList<Member> enrolledMembers;
	private ArrayList<Book> bookCollection;
	private int memberNo = 100000;
    
	public Library() {
    	this.enrolledMembers = new ArrayList<Member>();
    	this.bookCollection = new ArrayList<Book>();
	}
    
    
	public void getAllBooks(boolean fullString) {
    	// Prints either long or short string
   	 
    	// If no books in system
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
    	}else {
        	// If true, print the fullstring
        	if (fullString) {
            	for (int i = 0; i < this.bookCollection.size(); i++) {
                	System.out.println(this.bookCollection.get(i).longString());
                	if (i < this.bookCollection.size()-1)
                	System.out.println();
            	}
        	}else {  // If false, print the shortstring
            	for (Book b : this.bookCollection) {
                	System.out.println(b.shortString());
            	}
        	}
    	}
	}
    
    
	public void getAvailableBooks(boolean fullString) {
    	// Prints either long or short string
	 
 	// Notify if there are no books
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
     	return;
    	}

 	int noOfAva = 0;

    	// Parse through all books in library
    	for (int i = 0; i < this.bookCollection.size(); i++) {
        	// If available print string
        	if (this.bookCollection.get(i).getCurrentRenter() == null) {
         	noOfAva += 1;
            	// If true fullString, print full string, else print short string
            	if (fullString) {
                	System.out.println(this.bookCollection.get(i).longString());
                	if (i < this.bookCollection.size() - 1) {
                    	System.out.println();
                	}
            	} else {
                	System.out.println(this.bookCollection.get(i).shortString());
            	}
        	}
    	} if (noOfAva == 0) {
     	System.out.println("No books available.");
 	}
   	 
	}
    
    
	public void getCopies() {
    	// Prints the number of copies of each book
   	 
    	// Notify if there are no books
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
    	} else {
        	HashMap<String,Integer> copies = new HashMap<>();
       	 
        	// Add each string to copies and tally the duplicates
        	for (Book b : this.bookCollection) {
           	 
            	// Check similarity using short string
            	boolean hasCopy = false;
            	for (String copiesb : copies.keySet()) {
                	// If copies has the book, increment the number, else add book
                	if (copiesb.equals(b.shortString())) {
                    	copies.put(copiesb, copies.get(copiesb) + 1);
                    	hasCopy = true;
                 	break;
                	}
            	} if (hasCopy == false) {
                	copies.put(b.shortString(),1);
            	}
        	}
       	 
        	// Sort lexicographically by shortString
        	// Make new array with the short strings, sort the short strings
        	String[] shortstr = new String[copies.size()];
        	shortstr = copies.keySet().toArray(shortstr);
       	 
        	for (int i = 0; i < shortstr.length; i++) {
            	for (int j = i+1; j < shortstr.length; j++) {
                	if (shortstr[i].compareTo(shortstr[j]) > 0) {
                    	// Swap books
                    	String temp = shortstr[i];
                    	shortstr[i] = shortstr[j];
                    	shortstr[j] = temp;
                	}
            	}
        	}
        	// Match keys in copies to shortstr
        	LinkedHashMap<String,Integer> sortedCopies = new LinkedHashMap<>();
        	for (String s : shortstr) {
            	sortedCopies.put(s, copies.get(s));
        	}
       	 
        	// Print the number of copies
        	for (Map.Entry<String,Integer> e : sortedCopies.entrySet()) {
            	System.out.println(e.getKey() + ": " + e.getValue());
        	}
    	}
	}
    
    
	public void getGenres() {
    	// Prints all the genres
   	 
    	// Notify if no books in library
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
    	} else {
        	ArrayList<String> genres = new ArrayList<>();
        	for (Book b : this.bookCollection) {
            	// Add genre if it is not in genres
            	if (!genres.contains(b.getGenre())) {
                	genres.add(b.getGenre());
            	}
        	}
       	 
        	// Sort genres alphabetically
        	Collections.sort(genres, String.CASE_INSENSITIVE_ORDER);
       	 
        	// Print genres
        	for (String s : genres) {
            	System.out.println(s);
        	}
    	}
   	 
	}
    
    
	public void getAuthors() {
    	// Prints all the authors
   	 
    	// Notify if no books in library
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
    	} else {
        	ArrayList<String> authors = new ArrayList<>();
        	// Add author if it isn't in authors
        	for (Book b : this.bookCollection) {
            	if (!authors.contains(b.getAuthor())) {
                	authors.add(b.getAuthor());
            	}
        	}
       	 
        	// Sort authors alphabetically
        	Collections.sort(authors, String.CASE_INSENSITIVE_ORDER);
       	 
        	// Print authors
        	for (String s : authors) {
            	System.out.println(s);
        	}
    	}
	}
    
    
	public void getBooksByGenre(String genre) {
    	// Prints all books in specified genre
   	 
   	 if (this.bookCollection.size() < 1) {
      	System.out.println("No books in system.");
      	return;
   	 }
   	 
   	 List<Book> booksByGenre = Book.filterGenre(this.bookCollection, genre);
   	 
    	// Notify if no books from that genre
    	if (booksByGenre == null) {
   		 System.out.println("No books in system.");
    	} else if (booksByGenre.size() < 1) {
        	System.out.println("No books with genre " + genre + ".");
    	} else {  	 
        	// Print short strings
        	for (Book b : booksByGenre) {
            	System.out.println(b.shortString());
        	}
    	}
	}
    
    
	public void getBooksByAuthor(String author) {
    	// Prints all books by specified author
   	 
    	// Notify if no books in system
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
    	} else {
   		 List<Book> booksByAuthor = Book.filterAuthor(this.bookCollection, author);
       	 
        	// Notify if no books from that author
        	if (booksByAuthor == null) {
       		 System.out.println("No books in system.");
        	} else if (booksByAuthor.size() < 1) {
            	System.out.println("No books by " + author + ".");
        	} else {
       		 // Print Short string
            	for (Book b : booksByAuthor) {
                	System.out.println(b.shortString());
            	}
       	 
        	}
    	}
	}
    
    
	public void getBook(String serialNumber, boolean fullString) {
    	// Prints either long or short string of specified book
   	 
    	// Notify if no books in system
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
    	} else {
        	Book matchingB = null;
        	for (Book b : this.bookCollection) {
            	// find matching serial number
            	if (serialNumber.equals(b.getSerialNumber())) {
                	matchingB = b;
            	}
           	 
        	}
       	 
        	// Notify if no such book in system
        	if (matchingB == null) {
            	System.out.println("No such book in system.");
        	} else {
            	// Print full or short string
            	if (fullString) {
                	System.out.println(matchingB.longString());
            	} else {
                	System.out.println(matchingB.shortString());
            	}
        	}
    	}
	}
    
	public void bookHistory(String serialNumber) {
    	// Prints all previous members who have rented that book
   	 
    	// Notify if no books in system
    	if (this.bookCollection.size() < 1 || serialNumber == null) {
        	System.out.println("No such book in system.");
    	} else {
        	Book matchingB = null;
        	for (Book b : this.bookCollection) {
            	// find matching serial number
            	if (serialNumber.equals(b.getSerialNumber())) {
                	matchingB = b;
            	}
           	 
        	}
       	 
        	// Notify if no such book in system
        	if (matchingB == null) {
            	System.out.println("No such book in system.");
        	} else {
            	// Notify if book hasn't been rented
            	if (matchingB.renterHistory().size() < 1) {
                	System.out.println("No rental history.");
            	} else {
                	for (String m : matchingB.renterHistory()) {
                    	System.out.println(m);
                	}
            	}
        	}
    	}
	}
    
    
	public void addBook(String bookFile, String serialNumber) {
    	// Add a book to system by reading from csv file
   	 
    	if (bookFile == null) {
        	System.out.println("No such file.");
        	return;
    	}
   	 
    	if (serialNumber == null) {
        	System.out.println("No such book in file.");
        	return;
    	}
   	 
    	boolean doesExist = false;
    	// Notify if serial number is already in system
    	for (Book b : this.bookCollection) {
        	if (serialNumber.equals(b.getSerialNumber())) {
            	System.out.println("Book already exists in system.");
            	doesExist = true;
            	break;
        	}
    	}
   	 
    	if (!doesExist) {
   	 
        	File f = new File(bookFile);
       	 
        	try {
            	Scanner scan = new Scanner(f);
           	 
            	// Parse through each line to find matching serial number
            	Book chosenB = null;
            	// Deal with column titles
            	if (scan.hasNextLine()) {
                	scan.hasNextLine();
            	}
            	while (scan.hasNextLine()) {
                	String[] bInfo = scan.nextLine().split(",");
                	if (serialNumber.equals(bInfo[0])) {
                    	Book temp = new Book(bInfo[1], bInfo[2], bInfo[3], bInfo[0]);
                    	chosenB = temp;
                    	break;
                	}
            	}
           	 
            	scan.close();
           	 
            	// Notify if no book in file
            	if (chosenB == null) {
                	System.out.println("No such book in file.");
            	} else {
                	this.bookCollection.add(chosenB);
                	System.out.println("Successfully added: " + chosenB.shortString() + ".");
            	}
           	 
        	} catch (FileNotFoundException e) {
            	System.out.println("No such file.");
        	}
    	}
	}
    
    
	public void rentBook(String memberNumber, String serialNumber) {
    	// Loans a book to a member
   	 
    	// Notify if no members in system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
    	// Notify if no books in system
    	else if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
        	return;
    	}
   	 
    	// Notify if member doesn't exist
    	Member correctMember = null;
    	for (Member m : this.enrolledMembers) {
        	if (memberNumber.equals(m.getMemberNumber())) {
            	correctMember = m;
            	break;
        	}
    	}
    	if (correctMember == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	// Notify if book doesn't exist
    	Book correctB = null;
    	for (Book b : this.bookCollection) {
        	if (serialNumber.equals(b.getSerialNumber())) {
            	correctB = b;
            	break;
        	}
       	 
    	}
    	if (correctB == null) {
        	System.out.println("No such book in system.");
        	return;
    	}
   	 
    	// Notify if book is occupied
    	if (correctB.isRented()) {
        	System.out.println("Book is currently unavailable.");
        	return;
    	}
   	 
    	else {
        	correctB.rent(correctMember);
        	// Update currently rented in Member
        	correctMember.rent(correctB);
        	// Notify if successfully rented
        	System.out.println("Success.");
           	 
    	}
   	 
   	 
	}
    
    
	public void relinquishBook(String memberNumber, String serialNumber) {
    	// Returns a book
   	 
    	// Notify if no members in system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
    	// Notify if no books in system
    	else if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
        	return;
    	}
   	 
   	 
    	// Notify if member doesn't exist
    	if (memberNumber == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	Member correctMember = null;
    	for (Member m : this.enrolledMembers) {
        	if (memberNumber.equals(m.getMemberNumber())) {
            	correctMember = m;
            	break;
        	}
    	}
    	if (correctMember == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	// Notify if book doesn't exist    
    	if (serialNumber == null) {
        	System.out.println("No such book in system.");
        	return;
    	}

    	Book correctB = null;
    	for (Book b : this.bookCollection) {
        	if (serialNumber.equals(b.getSerialNumber())) {
            	correctB = b;
            	break;
        	}
       	 
    	}
    	if (correctB == null) {
        	System.out.println("No such book in system.");
        	return;
    	}
   	 
    	// Notify if member isn't loaning book
    	if (correctB.relinquish(correctMember) && correctMember.relinquish(correctB)) {
        	System.out.println("Success.");
    	}
    	else {
        	System.out.println("Unable to return book.");
    	}
	}
    
    
	public void relinquishAll(String memberNumber) {
    	// Return all books from a member
   	 
    	// Notify if no members in system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
   	 
    	// If memberNumber is null
    	if (memberNumber == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	// Notify if member doesn't exist
    	Member correctMember = null;
    	for (Member m : this.enrolledMembers) {
        	if (memberNumber.equals(m.getMemberNumber())) {
            	correctMember = m;
            	break;
        	}
    	}
    	if (correctMember == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	correctMember.relinquishAll();
  	 
    	System.out.println("Success.");
   	 
	}
    
    
	public void getMember(String memberNumber) {
    	// Prints details of the member
   	 
    	// Notify if no members in the system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
   	 
    	// Notify if member doesn't exist
    	Member correctMember = null;
    	for (Member m : this.enrolledMembers) {
        	if (memberNumber.equals(m.getMemberNumber())) {
            	correctMember = m;
            	break;
        	}
    	}
    	if (correctMember == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	// Print member number and name
    	System.out.println(correctMember.getMemberNumber() + ": " + correctMember.getName());
	}
    
    
	public void getMemberBooks(String memberNumber) {
    	// Prints all the books a member is renting
   	 
    	// Notify if no members in the system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
   	 
    	// Notify if member doesn't exist
    	Member correctMember = null;
    	for (Member m : this.enrolledMembers) {
        	if (memberNumber.equals(m.getMemberNumber())) {
            	correctMember = m;
            	break;
        	}
    	}
    	if (correctMember == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	// Notify if member is not renting any books
    	if (correctMember.getCurrentlyRenting().size() < 1) {
        	System.out.println("Member not currently renting.");
        	return;
    	}
   	 
    	// Print books' short string
    	for (Book b : correctMember.getCurrentlyRenting()) {
        	System.out.println(b.shortString());
    	}
	}
    
    
	public void memberRentalHistory(String memberNumber) {
    	// Prints all books a member has previously rented
   	 
    	// Notify if no members in the system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
   	 
    	// Notify if member doesn't exist
    	Member correctMember = null;
    	for (Member m : this.enrolledMembers) {
        	if (memberNumber.equals(m.getMemberNumber())) {
            	correctMember = m;
            	break;
        	}
    	}
    	if (correctMember == null) {
        	System.out.println("No such member in system.");
        	return;
    	}
   	 
    	// Notify if rental history is empty
    	if (correctMember.getRentHistory().size() < 1) {
        	System.out.println("No rental history for member.");
        	return;
    	}
   	 
    	// Print short string of book
    	for (Book b : correctMember.getRentHistory()) {
        	System.out.println(b.shortString());
    	}
    
	}
    
    
	public void addMember(String name) {
    	// Adds a member to the system
    	Member temp = new Member(name, Integer.toString(this.memberNo));
    	this.memberNo += 1;
    	this.enrolledMembers.add(temp);
    	System.out.println("Success.");
	}
    
    
	public void saveCollection(String filename) {
    	// Saves the current collection of books to a csv file
   	 
   	 
    	if (filename == null) {
        	return;
    	}
   	 
    	// Notify if no books in collection
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
        	return;
    	}
   	 
    	Book.saveBookCollection(filename, this.bookCollection);
    	System.out.println("Success.");
   	 
	}
    
    
	public void addCollection(String filename) {
    	// Adds the collection of books from a csv file
   	 
    	if (filename == null) {
        	System.out.println("No such collection.");
        	return;
    	}
   	 
    	List<Book> bookList = Book.readBookCollection(filename);
    	if (bookList == null) {
        	System.out.println("No such collection.");
        	return;
    	}
   	 
    	if (bookList.size() < 2) {
        	System.out.println("No books have been added to the system.");
        	return;
    	}

    	// Add unique books
    	int booksAdded = 0;
    	for (int i = 0; i < bookList.size(); i++) {
        	boolean isDuplicated = false;
        	Book checkingB = bookList.get(i);
        	for (Book oldB : this.bookCollection) {
            	if (checkingB.getSerialNumber().equals(oldB.getSerialNumber())) {
                	isDuplicated = true;
                	break;
            	}
        	}
       	 
        	if (!isDuplicated) {
            	this.bookCollection.add(checkingB);
            	booksAdded += 1;
        	}
    	}
  	 
   	 
    	if (booksAdded < 1) {
        	System.out.println("No books have been added to the system.");
    	} else {
        	System.out.println(booksAdded + " books successfully added.");
    	}
	}
    
    
    
    
	public void common(String[] memberNumbers) {
    	// Prints all the books that all the members provided have rented
   	 
    	// Notify if no members in the system
    	if (this.enrolledMembers.size() < 1) {
        	System.out.println("No members in system.");
        	return;
    	}
   	 
    	// Notify if no books in the system
    	if (this.bookCollection.size() < 1) {
        	System.out.println("No books in system.");
        	return;
    	}
   	 
   	 
    	Set<String> set = new HashSet<>(Arrays.asList(memberNumbers));
   	 
    	// Make an array with corresponding member objects from numbers
    	Member[] members = new Member[memberNumbers.length];
    	for (int i = 0; i < memberNumbers.length; i++) {
        	Member temp = null;
        	for (Member m : this.enrolledMembers) {
            	if (memberNumbers[i].equals(m.getMemberNumber())) {
                	temp = m;
                	members[i] = temp;
                	break;
            	}
        	}
        	// Notify if no such member
        	if (temp == null) {
            	System.out.println("No such member in system.");
            	return;
        	}
    	}
   	 
    	// Check if duplicate members
    	if (set.size() != memberNumbers.length) {
        	System.out.println("Duplicate members provided.");
        	return;
    	}
   	 
   	 
    	// Get common books
    	List<Book> commonB = Member.commonBooks(members);
   	 
    	// Notify if no common books
    	if (commonB == null) {
   		 System.out.println("No such member in system.");
    	} else if (commonB.size() < 1) {
        	System.out.println("No common books.");
    	} else {
        	// Sort by serial number
    	commonB = Book.sortBySerial(commonB);
   	 
    	// Print books' short string
    	for (Book b : commonB) {
        	System.out.println(b.shortString());
    	}
    	}
   	 
	}
    

    
	public static void main(String[] args) {
    	// Main method of the program
   	 
    	Scanner scan = new Scanner(System.in);
   	 
    	// Create a library
    	Library lib = new Library();
   	 
    	while (true) {
        	System.out.print("user: ");
     	String[] input;
    	 
        	// Get input
     	if (scan.hasNextLine()) {
            	input = scan.nextLine().split(" ");
     	}
     	else {
         	break;
     	}
       	 
        	// End program if "Exit"
        	if (input.length == 1 && input[0].toUpperCase().equals("EXIT")) {
            	System.out.println("Ending Library process.");
            	break;
        	}
       	 
        	// Print help statement
        	if (input.length == 1 && input[0].toUpperCase().equals("COMMANDS")) {
            	System.out.println(HELP_STRING);
            	System.out.println();
            	continue;
        	}
       	 
        	// Check for different commands
        	if (input.length >= 2) {
            	if (input.length == 2) {
                	if (input[0].toUpperCase().equals("NUMBER") &&
                        	input[1].toUpperCase().equals("COPIES")) {
                    	// Call getCopies()
                    	lib.getCopies();
                    	System.out.println();
                    	continue;
                	} else if (input[0].toUpperCase().equals("LIST") &&
                        	input[1].toUpperCase().equals("GENRES")) {
                    	// Call getGenres()
                    	lib.getGenres();
                    	System.out.println();
                    	continue;
                	} else if (input[0].toUpperCase().equals("LIST") &&
                        	input[1].toUpperCase().equals("AUTHORS")) {
                    	// Call getAuthors()
                    	lib.getAuthors();
                    	System.out.println();
                    	continue;
                	} else if (input[0].toUpperCase().equals("MEMBER") &&
                        	!(input[1].toUpperCase().equals("BOOKS") ||
                                	input[1].toUpperCase().equals("HISTORY"))) {
                    	// Call getMember()
                    	lib.getMember(input[1]);
                    	System.out.println();
                    	continue;
                	}
            	}
            	if (input[0].toUpperCase().equals("GENRE")) {
                	// Call getBooksByGenre()
                	lib.getBooksByGenre(input[1]);
                	System.out.println();
                	continue;
            	} else if (input[0].toUpperCase().equals("AUTHOR")) {
                	// Get author name
                	String name = "";
                	for (int i = 1; i < input.length; i++) {
                    	if (i == input.length - 1) {
                        	name += input[i];
                    	} else {
                        	name += input[i] + " ";
                    	}
                	}
               	 
                	// Call getBooksByAuthor()
                	lib.getBooksByAuthor(name);
                	System.out.println();
                	continue;
            	} else if (input[0].toUpperCase().equals("LIST") &&
                    	input[1].toUpperCase().equals("ALL")) {
                	// Call getAllBooks()
                	if (input.length == 3) {
                    	if (input[2].toUpperCase().equals("LONG")) {
                        	lib.getAllBooks(true);
                        	System.out.println();
                        	continue;
                    	}
                   	 
                	} else {
                    	lib.getAllBooks(false);
                    	System.out.println();
                    	continue;
                	}
            	} else if (input[0].toUpperCase().equals("LIST") &&
                    	input[1].toUpperCase().equals("AVAILABLE")) {
                	// Call getAvailableBooks()
                	if (input.length == 3) {
                    	if (input[2].toUpperCase().equals("LONG")) {
                        	lib.getAvailableBooks(true);
                        	System.out.println();
                        	continue;
                    	}
               	 
                	} else {
                    	lib.getAvailableBooks(false);
                    	System.out.println();
                    	continue;
                	}
            	} else if (input[0].toUpperCase().equals("BOOK")) {
                	if (input[1].toUpperCase().equals("HISTORY")) {
                    	if (input.length == 3) {
                        	// Call bookHistory()
                    	lib.bookHistory(input[2]);
                    	System.out.println();
                    	continue;
                    	}
                	} else if (input.length <= 3) {
                    	// Call getBook()
                    	if (input.length == 3) {
                        	if (input[2].toUpperCase().equals("LONG")) {
                            	lib.getBook(input[1], true);
                            	System.out.println();
                            	continue;
                        	}
                    	} else {
                        	lib.getBook(input[1], false);
                        	System.out.println();
                        	continue;
                    	}
                	}
            	}
           	 
            	if (input.length == 4) {
                	if (input[0].toUpperCase().equals("ADD") && input[1].toUpperCase().equals("BOOK")) {
                    	// Call addBook()
                    	lib.addBook(input[2], input[3]);
                    	System.out.println();
                    	continue;
                	}
            	}
           	 
            	if (input.length == 3) {
                	if (input[0].toUpperCase().equals("RENT")) {
                    	// Call rentBook()
                    	lib.rentBook(input[1], input[2]);
                    	System.out.println();
                    	continue;
                	} else if (input[0].toUpperCase().equals("RELINQUISH")) {
                    	if (input[1].toUpperCase().equals("ALL")) {
                        	// Call relinquishALl()
                        	lib.relinquishAll(input[2]);
                        	System.out.println();
                        	continue;
                    	} else {
                    	// Call relinquishBook()
                    	lib.relinquishBook(input[1], input[2]);
                    	System.out.println();
                    	continue;
                    	}
                	} else if (input[0].toUpperCase().equals("MEMBER")) {
                    	if (input[1].toUpperCase().equals("BOOKS")) {
                        	// Call getMemberBooks
                        	lib.getMemberBooks(input[2]);
                        	System.out.println();
                        	continue;
                    	} else if (input[1].toUpperCase().equals("HISTORY")) {
                        	// Call memberRentalHistory()
                        	lib.memberRentalHistory(input[2]);
                        	System.out.println();
                        	continue;
                    	}
                	} else if (input[0].toUpperCase().equals("SAVE") &&
                        	input[1].toUpperCase().equals("COLLECTION")) {
                    	// Call saveCollection()
                    	lib.saveCollection(input[2]);
                    	System.out.println();
                    	continue;
                	} else if (input[0].toUpperCase().equals("ADD") &&
                        	input[1].toUpperCase().equals("COLLECTION")) {
                    	// Call addCollection()
                    	lib.addCollection(input[2]);
                    	System.out.println();
                    	continue;
                	}
               	 
            	}
           	 
            	if (input[0].toUpperCase().equals("COMMON")) {
                	// Create String[] of membernumbers
                	String[] memberNumbers = new String[input.length-1];
                	for (int i = 1; i < input.length; i++) {
                    	memberNumbers[i-1] = input[i];
                	}
                	// Call common()
                	lib.common(memberNumbers);
                	System.out.println();
                	continue;
               	 
            	} else if (input[0].toUpperCase().equals("ADD") &&
                    	input[1].toUpperCase().equals("MEMBER") &&
                    	input.length != 2){
                	// Turn name string array into string
                	String name = "";
                	for (int i = 2; i < input.length; i++) {
                    	if (i == input.length - 1) {
                        	name += input[i];
                    	} else {
                        	name += input[i] + " ";
                    	}
                	}
                	// Call addMember()
                	lib.addMember(name);
                	System.out.println();
                	continue;
            	}
   	 
               	 
        	}
       	 
        	System.out.println();
       	 
    	}
   	 
    	scan.close();
	}
 }
