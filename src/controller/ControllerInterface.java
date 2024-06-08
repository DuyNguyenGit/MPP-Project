package controller;

import business.Book;
import business.CheckoutRecord;
import exception.LibrarySystemException;
import exception.LoginException;

import java.util.List;

public interface ControllerInterface {

    void login(String id, String password) throws LoginException;

    List<String> allMemberIds();

    List<String> allBookIds();

    List<CheckoutRecord> loadCheckoutRecord();

    Book addNewBookCopy(String isbn) throws LibrarySystemException;

    List<CheckoutRecord> checkoutForm(String memberId, String isbn) throws Exception;

    void addNewLibraryMember(String fname, String lname, String mId, String tel, String street, String city,
                             String state,
                             String zip) throws LibrarySystemException;
}
