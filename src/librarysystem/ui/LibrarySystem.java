package librarysystem.ui;

import controller.ControllerInterface;
import controller.SystemController;
import dataaccess.Auth;
import dataaccess.User;
import utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;


public class LibrarySystem extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, logout, allBookIds, allMemberIds, addBookCopy;
    String pathToImage;
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            AllMemberIdsWindow.INSTANCE,
            AllBookIdsWindow.INSTANCE,
            AddBookCopyWindow.INSTANCE
    };

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    private LibrarySystem() {
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();

        createMenus();
        //pack();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        pathToImage = currDirectory + "/src/resources/library.jpg";
    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());

        User currentUser = Util.getCurrentUser();
        if (currentUser == null) {
            addLoginMenuItem();
        } else if (Auth.ADMIN.equals(currentUser.getAuthorization())) {
            addAdministratorMenuItems();
        } else if (Auth.LIBRARIAN.equals(currentUser.getAuthorization())) {
            addLibrarianMenuItems();
        } else {
            addLoggedInMenuItems();
        }

        setJMenuBar(menuBar);
    }

    private void addLoginMenuItem() {
        options = new JMenu("Options");
        menuBar.add(options);

        login = new JMenuItem("Login");
        login.addActionListener(new LoginListener());
        options.add(login);
    }

    private void addLogoutMenuItem() {
        logout = new JMenuItem("Logout");
        logout.addActionListener(new LogoutListener());
        options.add(logout);
    }

    private void addAdministratorMenuItems() {
        // Administrators are able to add/edit member info and add books to the collection,
        // but they are not allowed to checkout books for a member (unless they also have Librarian access).
        options = new JMenu("Options");
        menuBar.add(options);

        allBookIds = new JMenuItem("All Book Ids");
        allBookIds.addActionListener(new AllBookIdsListener());
        options.add(allBookIds);

        allMemberIds = new JMenuItem("All Member Ids");
        allMemberIds.addActionListener(new AllMemberIdsListener());
        options.add(allMemberIds);

        addLogoutMenuItem();
    }

    private void addLibrarianMenuItems() {
        // Librarians are allowed to checkout books
        // but not allowed to add/edit members or add books (unless they also have Administrator access).
        options = new JMenu("Options");
        menuBar.add(options);

        addBookCopy = new JMenuItem("Add Book Copy");
        addBookCopy.addActionListener(new AddBookCopyListener());
        options.add(addBookCopy);

        addLogoutMenuItem();
    }

    private void addLoggedInMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);

        allBookIds = new JMenuItem("All Book Ids");
        allBookIds.addActionListener(new AllBookIdsListener());
        options.add(allBookIds);

        allMemberIds = new JMenuItem("All Member Ids");
        allMemberIds.addActionListener(new AllMemberIdsListener());
        options.add(allMemberIds);

        addBookCopy = new JMenuItem("Add Book Copy");
        addBookCopy.addActionListener(new AddBookCopyListener());
        options.add(addBookCopy);

        addLogoutMenuItem();
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        }
    }

    class LogoutListener extends AuthorizationListener {

        @Override
        public void handleNextStep(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            Util.setCurrentUser(null);
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        }

        @Override
        boolean isAllowedLibrarian() {
            return true;
        }

        @Override
        boolean isAllowedAdmin() {
            return true;
        }
    }

    abstract class AuthorizationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            User currentUser = Util.getCurrentUser();
            if (currentUser != null) {
                if (isAllowedAdmin() && (Auth.ADMIN.equals(currentUser.getAuthorization()) || Auth.BOTH.equals(currentUser.getAuthorization()))) {
                    handleNextStep(e);
                    return;
                }

                if (isAllowedLibrarian() && (Auth.LIBRARIAN.equals(currentUser.getAuthorization()) || Auth.BOTH.equals(currentUser.getAuthorization()))) {
                    handleNextStep(e);
                    return;
                }
            }

            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);
        }

        abstract void handleNextStep(ActionEvent e);

        abstract boolean isAllowedAdmin();

        abstract boolean isAllowedLibrarian();
    }

    class AllBookIdsListener extends AuthorizationListener {

        @Override
        public void handleNextStep(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllBookIdsWindow.INSTANCE.setData(sb.toString());
            AllBookIdsWindow.INSTANCE.pack();
            //AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);
        }

        @Override
        boolean isAllowedAdmin() {
            return true;
        }

        @Override
        boolean isAllowedLibrarian() {
            return false;
        }
    }

    class AllMemberIdsListener extends AuthorizationListener {

        @Override
        public void handleNextStep(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllMemberIdsWindow.INSTANCE.init();
            AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);


            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            //AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);
        }

        @Override
        boolean isAllowedAdmin() {
            return true;
        }

        @Override
        boolean isAllowedLibrarian() {
            return false;
        }
    }

    class AddBookCopyListener extends AuthorizationListener {

        @Override
        public void handleNextStep(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AddBookCopyWindow.INSTANCE.init();
            AddBookCopyWindow.INSTANCE.pack();
            AddBookCopyWindow.INSTANCE.setVisible(true);
            Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
        }

        @Override
        boolean isAllowedAdmin() {
            return false;
        }

        @Override
        boolean isAllowedLibrarian() {
            return true;
        }
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
}
