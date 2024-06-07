package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import exception.LoginException;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<CheckoutRecord> checkoutForm(String memberId, String isbn) throws Exception;

	List<CheckoutRecord> loadCheckoutRecord();
}
