import java.util.*;

public class BookRenterHist {
    
    public Library lib = new Library();
    public Book mock = new Book("Mockingbird", "Harper", "Bildungsroman", "111111");
    public Member john = new Member("john", "100000");
    public Member hermionie = new Member("hermionie", "100001");


    public static void test1() {
        List<Book> books = Book.readBookCollection("duplicates.csv");
        
        for (Book b : books){
            System.out.println(b.longString());
        }

    }

    public static void test2() {
        Book.saveBookCollection("qwerty.csv", Book.readBookCollection("duplicates.csv") );

    }

    public static void main(String[] args){
        // test1();
        test2();
    }

}
