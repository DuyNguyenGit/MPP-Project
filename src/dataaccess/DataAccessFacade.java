package dataaccess;

import business.Book;
import business.LibraryMember;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.*;


public class DataAccessFacade implements DataAccess {
	
	enum StorageType {
		BOOKS, MEMBERS, USERS, CHECKOUTRECORD;
	}

    enum StorageType {
        BOOKS, MEMBERS, USERS, CURRENT_USER;
    }

    public static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/src/dataaccess/storage";
    public static final String DATE_PATTERN = "MM/dd/yyyy";

	public HashMap<String, CheckoutRecord> saveNewCheckoutRecord(CheckoutRecord checkoutRecord) {
		HashMap<String, CheckoutRecord> checkoutRecordHashMap = readCheckoutRecordMap();
		if(checkoutRecordHashMap == null){
			checkoutRecordHashMap = new HashMap<>();
		}
		String memberId = checkoutRecord.getLibraryMember().getMemberId();
		checkoutRecordHashMap.put(memberId, checkoutRecord);
		saveToStorage(StorageType.CHECKOUTRECORD, checkoutRecordHashMap);
		return checkoutRecordHashMap;
	}

	@Override
	public void updateBookCopyAvailability(String isbn, BookCopy checkoutBookCopy) {
		HashMap<String, Book> books = readBooksMap();
		BookCopy[] bookCopies = books.get(isbn).getCopies();
		int newCopyNum = checkoutBookCopy.getCopyNum();

		for(BookCopy bookCopy: bookCopies){
			if(bookCopy.getCopyNum() == newCopyNum){
				bookCopy.changeAvailability();
			}
		}
		saveToStorage(StorageType.BOOKS, books);
	}
	@Override
	public LibraryMember searchMember(String memberId) {
		HashMap<String, LibraryMember> members = (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
		if (members != null) {
			return members.get(memberId);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap() {
		return (HashMap<String,CheckoutRecord>) readFromStorage(StorageType.CHECKOUTRECORD);
	}

    public void saveCurrentUser(User user) {
        HashMap<String, User> currentUser = new HashMap<>();
        if (user != null) {
            String userId = user.getId();
            currentUser.put(userId, user);
        }
        saveToStorage(StorageType.CURRENT_USER, currentUser);
    }

    public User getCurrentUser() {
        HashMap<String, User> currentUser = readCurrentUserMap();
        if (currentUser != null && !currentUser.isEmpty()) {
            return currentUser.values().iterator().next();
        }
        return null;
    }
	
	
    /**
     * read all books
     *
     * @return Map<String, Book>
     */
    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, Book> readBooksMap() {
        //Returns a Map with name/value pairs being
        //   isbn -> Book
        return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
    }

    // implement: other save operations
    public void saveNewMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = readMemberMap();
        if (mems != null) {
            String memberId = member.getMemberId();
            mems.put(memberId, member);
            saveToStorage(StorageType.MEMBERS, mems);
        }
    }

    @Override
    public LibraryMember searchMember(String memberId) {
        HashMap<String, LibraryMember> members = (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
        if (members != null) {
            return members.get(memberId);
        }
        return null;
    }

    @Override
    public Book searchBook(String isbn) {
        HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
        return books.get(isbn);
    }

    @Override
    public void saveBook(Book book) {
        HashMap<String, Book> books = readBooksMap();
        if (books != null) {
            String bookisbn = book.getIsbn();
            books.put(bookisbn, book);
            saveToStorage(StorageType.BOOKS, books);
        }
    }

    /**
     * read current user
     *
     * @return Map<String, User>
     */
    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, User> readCurrentUserMap() {
        return (HashMap<String, User>) readFromStorage(StorageType.CURRENT_USER);
    }

    /**
     * read all members
     *
     * @return Map<String, Book>
     */
    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        //Returns a Map with name/value pairs being
        //   memberId -> LibraryMember
        return (HashMap<String, LibraryMember>) readFromStorage(
                StorageType.MEMBERS);
    }

    /**
     * read all users
     *
     * @return Map<String, Book>
     */
    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        //Returns a Map with name/value pairs being
        //   userId -> User
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }


	static void saveToStorage(StorageType type, HashMap<? extends String, ? extends Serializable> ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

    static void loadUserMap(List<dataaccess.User> userList) {
        HashMap<String, dataaccess.User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(StorageType.USERS, users);
    }

    static void loadMemberMap(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(StorageType.MEMBERS, members);
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static Object readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }


    final static class Pair<S, T> implements Serializable {

        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) return false;
            if (this == ob) return true;
            if (ob.getClass() != getClass()) return false;
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }

        private static final long serialVersionUID = 5399827794066637059L;
    }

}
