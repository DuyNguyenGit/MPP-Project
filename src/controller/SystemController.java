package controller;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exception.LibrarySystemException;
import exception.LoginException;
import service.IBookService;
import service.impl.BookService;
import utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SystemController implements ControllerInterface {

    private DataAccess dataAccess = new DataAccessFacade();

    public User login(String id, String password) throws LoginException {
        if (Util.isEmpty(id) || Util.isEmpty(password)) {
            throw new LoginException("ID and Password must be not empty");
        }

        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        return map.get(id);
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
    public Book addNewBookCopy(String isbn) throws LibrarySystemException {
        if (Util.isEmpty(isbn)) {
            throw new LibrarySystemException("ISBN must be not empty");
        }

        IBookService bookService = new BookService();
        Optional<Book> bookOpt = bookService.searchBookByIsbn(isbn);
        if (bookOpt.isEmpty()) {
            throw new LibrarySystemException("Book not found, please enter correct ISBN");
        }

        return bookService.saveNewBookCopy(bookOpt.get());
    }
}
