package dataaccess;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

<<<<<<< .mine
import java.util.HashMap;

public interface DataAccess {
=======
import java.util.HashMap;

    public HashMap<String, User> readUserMap();

    public HashMap<String, LibraryMember> readMemberMap();

    public HashMap<String, User> readCurrentUserMap();

    public void saveNewMember(LibraryMember member);

    public void updateNewBook(Book book);

    public void saveCurrentUser(User user);

    public User getCurrentUser();

    public LibraryMember searchMember(String memberId);

    public Book searchBook(String isbn);

    public void saveBook(Book book);

    public HashMap<String, CheckoutRecord> readCheckoutRecordMap();

	public void saveNewMember(LibraryMember member);
	
    public HashMap<String, CheckoutRecord> saveNewCheckoutRecord(CheckoutRecord checkoutRecordHashMap);
	
    public void updateBookCopyAvailability(String isbn, BookCopy checkoutBookCopy);
}
