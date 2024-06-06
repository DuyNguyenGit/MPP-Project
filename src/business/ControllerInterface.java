package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
    public void login(String id, String password) throws LoginException;

    public List<String> allMemberIds();

    public List<String> allBookIds();

    public void checkOutBook(String memberId, String isbn) throws LibrarySystemException;

    void addBookCopy(String isbn, int noOfCopies) throws LibrarySystemException;

    void addBook(String isbn, String title, List<String> authorList, String borrowDays, String bookCopiesUnit) throws LibrarySystemException;
}
