package controller;

import business.*;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exception.LibrarySystemException;
import exception.LoginException;
import service.IBookService;
import service.impl.BookService;
import utils.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;

    private DataAccess dataAccess = new DataAccessFacade();

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
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusDays(bookMap.get(isbn).getMaxCheckoutLength());

        CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(checkoutDate, dueDate, checkoutBookCopy);
        CheckoutRecord checkoutRecord = new CheckoutRecord(libraryMember,checkoutRecordEntry,dateTime);
        da.updateBookCopyAvailability(isbn, checkoutBookCopy);

        HashMap<String, CheckoutRecord> checkoutRecordHashMap = da.saveNewCheckoutRecord(checkoutRecord,
                checkoutBookCopy.getCopyNum());
        List<CheckoutRecord> checkoutRecords = checkoutRecordHashMap.values().stream().toList();

        return checkoutRecords;
    }

    private BookCopy checkAvailableBookCopy(HashMap<String, Book> bookMap, String isbn) throws LibrarySystemException {
        BookCopy[] bookCopies = bookMap.get(isbn).getCopies();
        for (BookCopy bookCopy : bookCopies) {
            if (bookCopy.getAvailableBookCopies() ) {
                return bookCopy;
            }
        }
        throw new LibrarySystemException("This book is out of copies");
    }

    @Override
    public List<CheckoutRecord> loadCheckoutRecord() {
        HashMap<String, CheckoutRecord> checkoutRecordHashMap = dataAccess.readCheckoutRecordMap();
        if(checkoutRecordHashMap == null){
            checkoutRecordHashMap = new HashMap<>();
        }
        List<CheckoutRecord> sortedRecords = checkoutRecordHashMap.values().stream()
                .sorted(Comparator.comparing(CheckoutRecord::getDateTime))
                .collect(Collectors.toList());
        return sortedRecords;
    }

    public static String[][] allMembers() {
        DataAccess da = new DataAccessFacade();
        List<LibraryMember> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().values());
        String[][] results = new String[retval.size()][5];
        int i = 0;
        for (LibraryMember lb : retval) {
            String[] value = new String[5];
            value[0] = (i + 1) + "";
            value[1] = lb.getMemberId();
            value[2] = lb.getFirstName();
            value[3] = lb.getLastName();
            value[4] = lb.getTelephone();
            results[i] = value;
            i++;
        }

        return results;
    }

    public static String[][] allBooks() {
        DataAccess da = new DataAccessFacade();
        List<Book> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().values());
        String[][] results = new String[retval.size()][5];
        int i = 0;
        for (Book lb : retval) {
            String[] value = new String[5];
            value[0] = (i + 1) + "";
            value[1] = lb.getIsbn();
            value[2] = lb.getTitle();
            StringBuilder authors = new StringBuilder();
            lb.getAuthors().forEach(e -> {
                authors.append(e.getFirstName()).append(" ").append(e.getLastName()).append(", ");
            });
            int bookCopy = 0;
            for(BookCopy e: lb.getCopies()){
                if(e.getAvailableBookCopies()) bookCopy++;
            }
            value[3] = !authors.isEmpty() ? authors.toString().substring(0, authors.length() - 2) : "";
            value[4] = String.valueOf(bookCopy);
            results[i] = value;
            i++;
        }

        return results;
    }

    @Override
    public void addNewLibraryMember(String fname, String lname, String mId, String tel, String street, String city,
                                    String state,
                                    String zip) throws LibrarySystemException {
        LibraryMember libraryMember = dataAccess.searchMember(mId);
        if (libraryMember != null) {
            throw new LibrarySystemException("Member already exist");
        }

        if (fname.trim().isEmpty() || lname.trim().isEmpty() || mId.trim().isEmpty() || tel.trim().isEmpty()
                || street.trim().isEmpty() || city.trim().isEmpty() || state.trim().isEmpty() || zip.trim().isEmpty()) {
            throw new LibrarySystemException("All fields must be non-empty!");
        }

        String zipcoderegex = "^\\d{5}";
        if (!zip.matches(zipcoderegex)) {
            throw new LibrarySystemException("ZipCode is illegal");
        }
        String telePhoneRegex = "^\\d{3}-\\d{3}-\\d{4}$";
        if (!tel.matches(telePhoneRegex)) {
            throw new LibrarySystemException("telephone number input is illegal");
        }
        String mIDRegexString = "^\\d{4}";
        if (!mId.matches(mIDRegexString)) {
            throw new LibrarySystemException("Member ID should be four digits");
        }

        char[] chs = mId.toCharArray();
        for (char ch : chs) {
            if (ch < '0' || ch > '9') {
                throw new LibrarySystemException("Member Id must be numeric");
            }
        }

        Address address = new Address(street, city, state, zip);
        LibraryMember member = new LibraryMember(mId, fname, lname, tel, address);

        DataAccess da = new DataAccessFacade();
        da.saveNewMember(member);

    }
}
