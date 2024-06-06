package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{

    private static final long serialVersionUID = 111828999272875133L;
	private List<CheckoutRecordEntry> checkoutRecordEntries;

    public CheckoutRecord() {
        this.checkoutRecordEntries = new ArrayList<>();
    }

    public void addEntry(CheckoutRecordEntry checkoutRecordEntry) {
        checkoutRecordEntries.add(checkoutRecordEntry);
    }

    public CheckoutRecordEntry getEntryByBookCopy(BookCopy copy) {
    	for (CheckoutRecordEntry checkoutRecordEntry : checkoutRecordEntries) {
			if(copy.equals(checkoutRecordEntry.getCopy())) return checkoutRecordEntry;
		}
    	return null;
    }
	
	public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
		return checkoutRecordEntries;
	}

	public void setCheckoutRecordEntries(List<CheckoutRecordEntry> checkoutRecordEntries) {
		this.checkoutRecordEntries = checkoutRecordEntries;
	}
    
    
}
