package librarysystem;

import dataaccess.Auth;

import javax.swing.*;
import java.io.Serializable;

public class WindowScreen extends JFrame implements Serializable {

    public WindowScreen(Auth auth) {
            setTitle("Dashboard");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Create menu bar
            JMenuBar menuBar = new JMenuBar();

            // Create menus based on the auth role
            if (auth == Auth.ADMIN || auth == Auth.BOTH) {
                JMenu adminMenu = new JMenu("Admin");
                JMenuItem addMember = new JMenuItem("Add Member");
                JMenuItem editMember = new JMenuItem("Edit Member");
                JMenuItem addBook = new JMenuItem("Add Book");
                adminMenu.add(addMember);
                adminMenu.add(editMember);
                adminMenu.add(addBook);
                menuBar.add(adminMenu);
            }

            if (auth == Auth.LIBRARIAN || auth == Auth.BOTH) {
                JMenu librarianMenu = new JMenu("Librarian");
                JMenuItem checkoutBook = new JMenuItem("Checkout Book");
                JMenuItem returnBook = new JMenuItem("Return Book");
                JMenuItem viewRecords = new JMenuItem("View Records");
                librarianMenu.add(checkoutBook);
                librarianMenu.add(returnBook);
                librarianMenu.add(viewRecords);
                menuBar.add(librarianMenu);
            }

            setJMenuBar(menuBar);
        }
    }

