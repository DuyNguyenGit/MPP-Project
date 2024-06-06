package dataaccess;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

import java.util.HashMap;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	LibraryMember searchMember(String memberId);
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap();
	public void saveNewMember(LibraryMember member);
	public HashMap<String, CheckoutRecord> saveNewCheckoutRecord(CheckoutRecord checkoutRecordHashMap);
	public void updateBookCopyAvailability(String isbn, BookCopy checkoutBookCopy);
}
