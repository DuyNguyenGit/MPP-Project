package business;

import java.io.Serializable;
import java.time.LocalDate;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;

	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.checkoutRecord = new CheckoutRecord();
	}


	public String getMemberId() {
		return memberId;
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;

	public void checkout(BookCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
		copy.setAvailable(false);
		CheckoutRecordEntry checkoutEntry = CheckoutRecordEntry.createEntry(copy, checkoutDate, dueDate);
		getCheckoutRecord().addEntry(checkoutEntry);
	}
}