package librarysystem.app_main;

import business.SystemController;
import dataaccess.Auth;
import librarysystem.*;
import librarysystem.windows.*;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel {
    public static AppPanel INSTANCE = new AppPanel();
    private JSplitPane splitPane;

    private JPanel leftLoginSide;
    private JPanel rightLoginSide;

    private JButton logoutBtn;

    private JList sideBarMenuList;

    private JPanel dashboard, addBookPanel, addMember, AllMemberIds, AllBook;
    private DashboardWindow dashboardWindow;
    private CheckOutBookWindow checkOutBookWindow;
    private AddBookCopyWindow addBookCopy;
    private CheckoutRecordWindow checkoutRecord;
    private BookCopiesWindow bookCopiesWindow;
    private String[] listMenu = {"Dashboard", "Add Member", "Add Book", "Add Book Copy", "Members", "Books",
            "CheckOut Book", "CheckOut Record", "Copies"};
    private String[] listAdminMenu = {"Dashboard", "Add Member", "Add Book", "Add Book Copy", "Members", "Books",
            "Copies"};
    private String[] listLibrarianMenu = {"Dashboard", "CheckOut Book", "CheckOut Record"};

    private AppPanel() {
        super(new CardLayout());
        initComponents();
    }

    private String[] getRoleMenu() {
        if (SystemController.currentAuth == Auth.ADMIN) {
            return listAdminMenu;
        } else if (SystemController.currentAuth == Auth.LIBRARIAN) {
            return listLibrarianMenu;
        } else {
            return listMenu;
        }
    }

    public void setRoleMenu() {
        leftLoginSide.removeAll();
        paintMenu();
        goToDashBoard();
    }

    private void setLeftAppSidePanel() {
        leftLoginSide = new JPanel();
        leftLoginSide.setBackground(Color.lightGray);
        paintMenu();
    }

    public void paintMenu() {
        sideBarMenuList = new JList<String>(getRoleMenu());
        sideBarMenuList.setBackground(Color.WHITE);
        sideBarMenuList.setForeground(Color.black);
        sideBarMenuList.setSelectedIndex(0);

        sideBarMenuList.setFixedCellHeight(40);

        sideBarMenuList.setSelectionForeground(Color.WHITE);
        sideBarMenuList.setSelectionBackground(Color.lightGray);

        sideBarMenuList.addListSelectionListener(event -> {
            ((CardLayout) (rightLoginSide.getLayout())).show(rightLoginSide,
                    sideBarMenuList.getSelectedValue().toString());
        });


        DefaultListCellRenderer renderer = (DefaultListCellRenderer) sideBarMenuList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.RED);
        // logoutBtn.setForeground(Color.WHITE);


        logoutBtn.addActionListener(e -> {
            MainLibrarySystem.getInstance().navigateToLogin();
        });

        leftLoginSide.add(sideBarMenuList);
        leftLoginSide.add(logoutBtn);

    }

    private void setRightAppSidePanel() {
        rightLoginSide = new JPanel(new CardLayout());
        rightLoginSide.setBackground(Color.gray);

        dashboard = DashboardWindow.INSTANCE;
        addBookPanel = AddBookWindow.INSTANCE;
        addMember = AddNewLibraryMemberWindow.INSTANCE;
        addBookCopy = AddBookCopyWindow.INSTANCE;
        AllMemberIds = AllMemberIdsWindow.INSTANCE;
        AllBook = AllBookIdsWindow.INSTANCE;
        checkOutBookWindow = CheckOutBookWindow.INSTANCE;
        checkoutRecord = CheckoutRecordWindow.INSTANCE;
        bookCopiesWindow = BookCopiesWindow.INSTANCE;

        rightLoginSide.add(listMenu[0], dashboard);
        rightLoginSide.add(listMenu[1], addMember);
        rightLoginSide.add(listMenu[2], addBookPanel);
        rightLoginSide.add(listMenu[3], addBookCopy);
        rightLoginSide.add(listMenu[4], AllMemberIds);
        rightLoginSide.add(listMenu[5], AllBook);
        rightLoginSide.add(listMenu[6], checkOutBookWindow);
        rightLoginSide.add(listMenu[7], checkoutRecord);
        rightLoginSide.add(listMenu[8], bookCopiesWindow);

    }

    public void goToDashBoard() {
        ((CardLayout) (rightLoginSide.getLayout())).show(rightLoginSide, listMenu[0]);
    }

    private void initComponents() {

        setRightAppSidePanel();
        setLeftAppSidePanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftLoginSide, rightLoginSide);
        splitPane.setDividerLocation(150);
        // Add the SplitPane to the Pane
        add(splitPane, BorderLayout.CENTER);

    }
}
