package business;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = -728095373785964854L;
    private LibraryMember libraryMember;
    private List<CheckoutRecordEntry> checkoutRecordEntryList = new ArrayList<>();
    private List<Fine> fineList = new ArrayList<>();

    public CheckoutRecord(LibraryMember libraryMember, CheckoutRecordEntry RecordEntry) {
        this.libraryMember = libraryMember;
        this.checkoutRecordEntryList.add(RecordEntry);
    }

    public void addEntry(CheckoutRecordEntry checkoutRecordEntry) {
        checkoutRecordEntryList.add(checkoutRecordEntry);
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public List<CheckoutRecordEntry> getCheckoutRecordEntryList() {
        return checkoutRecordEntryList;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public void setCheckoutRecordEntryList(List<CheckoutRecordEntry> checkoutRecordEntryList) {
        this.checkoutRecordEntryList = checkoutRecordEntryList;
    }
}
