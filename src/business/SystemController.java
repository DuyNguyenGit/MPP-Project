package business;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ReadOnlyBufferException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public List<CheckoutRecord> checkoutForm(String memberId, String isbn) throws Exception {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		HashMap<String, Book> bookMap = da.readBooksMap();
		if(!memberMap.containsKey(memberId)) {
			throw new LibrarySystemException("Member id with " + memberId + " is not found");
		}
		if(!bookMap.containsKey(isbn) || bookMap.get(isbn).getCopies() == null) {
			throw new LibrarySystemException("This book is not available");
		}

		BookCopy checkoutBookCopy = checkAvailableBookCopy(bookMap, isbn);

		LibraryMember libraryMember = memberMap.get(memberId);
		LocalDate checkoutDate = LocalDate.now();
		LocalDate dueDate = checkoutDate.plusDays(bookMap.get(isbn).getMaxCheckoutLength());

		CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(checkoutDate, dueDate, checkoutBookCopy);
		CheckoutRecord checkoutRecord = new CheckoutRecord(libraryMember,checkoutRecordEntry);
		da.updateBookCopyAvailability(isbn, checkoutBookCopy);

		HashMap<String, CheckoutRecord> checkoutRecordHashMap = da.saveNewCheckoutRecord(checkoutRecord);
		List<CheckoutRecord> checkoutRecords = checkoutRecordHashMap.values().stream().toList();

		return checkoutRecords;
	}

	private BookCopy checkAvailableBookCopy(HashMap<String, Book> bookMap, String isbn) throws LibrarySystemException {
		BookCopy[] bookCopies = bookMap.get(isbn).getCopies();
        for (BookCopy bookCopy : bookCopies) {
            if (bookCopy.getAvailableBookCopies()) {
                return bookCopy;
            }
        }
		throw new LibrarySystemException("This book is out of copies");
	}
}
