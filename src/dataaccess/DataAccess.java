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

public interface DataAccess { 
>>>>>>> .theirs
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	LibraryMember searchMember(String memberId);
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap();
	public void saveNewMember(LibraryMember member);
	public HashMap<String, CheckoutRecord> saveNewCheckoutRecord(CheckoutRecord checkoutRecordHashMap);
	public void updateBookCopyAvailability(String isbn, BookCopy checkoutBookCopy);
}
