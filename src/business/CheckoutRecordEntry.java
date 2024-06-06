package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable{

    private static final long serialVersionUID = -1417908710325021138L;
	private LocalDate checkoutDate;
    private LocalDate dueDate;

    private BookCopy copy;
    
    

 

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public BookCopy getCopy() {
		return copy;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void setCopy(BookCopy copy) {
		this.copy = copy;
	}

	public CheckoutRecordEntry(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
        this.copy = copy;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

	public static CheckoutRecordEntry createEntry(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		return new CheckoutRecordEntry(copy, checkoutDate, dueDate);
	}
}
