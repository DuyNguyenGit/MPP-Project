package controller;

import business.Book;
import dataaccess.User;
import exception.LibrarySystemException;
import exception.LoginException;

import java.util.List;

public interface ControllerInterface {

    public User login(String id, String password) throws LoginException;

    public List<String> allMemberIds();

    public List<String> allBookIds();

    public Book addNewBookCopy(String isbn) throws LibrarySystemException;

}
