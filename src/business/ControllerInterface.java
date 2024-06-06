package business;

import exception.LibrarySystemException;
import exception.LoginException;

import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<CheckoutRecord> checkoutForm(String memberId, String isbn) throws Exception;
}
