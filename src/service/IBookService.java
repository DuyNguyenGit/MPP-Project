package service;

import business.Book;

import java.util.Optional;

public interface IBookService {

    Optional<Book> searchBookByIsbn(String isbn);

    Book saveNewBookCopy(Book book);
}
