package dataaccess;

import java.util.HashMap;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap();
	public void saveNewMember(LibraryMember member);
	public HashMap<String, CheckoutRecord> saveNewCheckoutRecord(CheckoutRecord checkoutRecordHashMap);
	public void updateBookCopyAvailability(String isbn, BookCopy checkoutBookCopy);
}
