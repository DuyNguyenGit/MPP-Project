package service.impl;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import service.IBookService;

import java.util.HashMap;
import java.util.Optional;

public class BookService implements IBookService {

    @Override
    public Optional<Book> searchBookByIsbn(String isbn) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> bookMap = da.readBooksMap();

        if (bookMap.containsKey(isbn)) {
            return Optional.of(bookMap.get(isbn));
        }
        return Optional.empty();
    }

    @Override
    public Book saveNewBookCopy(Book book) {
        DataAccess da = new DataAccessFacade();
        book.addCopy();
        da.updateNewBook(book);
        return book;
    }
}
