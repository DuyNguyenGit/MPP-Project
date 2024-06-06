package business;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {
    @Serial
    private static final long serialVersionUID = 6550573558639754705L;

    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private BookCopy bookCopy;

    public CheckoutRecordEntry(LocalDate checkOutDate, LocalDate dueDate, BookCopy bookCopy) {
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BookCopy getBookNum() {
        return bookCopy;
    }

    public void setBookNum(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }
}
