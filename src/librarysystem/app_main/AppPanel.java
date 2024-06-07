package librarysystem.app_main;

import controller.SystemController;
import dataaccess.Auth;
import librarysystem.MainLibrarySystem;
import librarysystem.table.TableExample;
import librarysystem.windows.*;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel {
    public static AppPanel INSTANCE = new AppPanel();
    private JSplitPane splitPane;

    private JPanel leftSidePanel;
    private JPanel rightSidePanel;

    private JButton logoutBtn;

    private JList sideBarMenuList;

    private JPanel dashboard, addMember, allMemberIds, addBookCopy, allBook, checkOutBook, checkoutRecord;

    private String[] listMenu = {"Library", "Add Member", "Members", "Add Book Copy", "Books",
            "CheckOut Book", "CheckOut Record", "Copies"};
    private String[] listAdminMenu = {"Library", "Add Member", "Add Book", "Add Book Copy", "Members", "Books",
            "Copies"};
    private String[] listLibrarianMenu = {"Library", "CheckOut Book", "CheckOut Record"};

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
        leftSidePanel.removeAll();
        addMenu();
        goToDashBoard();
    }

    private void setLeftAppSidePanel() {
        leftSidePanel = new JPanel();
        leftSidePanel.setBackground(Color.LIGHT_GRAY);
        addMenu();
    }

    public void addMenu() {
        sideBarMenuList = new JList<String>(getRoleMenu());
        sideBarMenuList.setBackground(Color.WHITE);
        sideBarMenuList.setForeground(Color.black);
        sideBarMenuList.setSelectedIndex(0);

        sideBarMenuList.setFixedCellHeight(40);

        sideBarMenuList.setSelectionForeground(Color.WHITE);
        sideBarMenuList.setSelectionBackground(Color.lightGray);

        sideBarMenuList.addListSelectionListener(event -> {
           String sideBarComponent = sideBarMenuList.getSelectedValue().toString();
           if(sideBarComponent.equals(listMenu[3])) {
               AddBookCopyWindow.INSTANCE.clearFields();
               AddBookCopyWindow.INSTANCE.clearTable();
           }
           ((CardLayout) (rightSidePanel.getLayout())).show(rightSidePanel,
                    sideBarComponent);
        });


        DefaultListCellRenderer renderer = (DefaultListCellRenderer) sideBarMenuList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.RED);
        // logoutBtn.setForeground(Color.WHITE);


        logoutBtn.addActionListener(e -> {
            MainLibrarySystem.getInstance().navigateToLogin();
        });

        leftSidePanel.add(sideBarMenuList);
        leftSidePanel.add(logoutBtn);
    }

    private void setRightAppSidePanel() {
        rightSidePanel = new JPanel(new CardLayout());
        rightSidePanel.setBackground(Color.WHITE);

        dashboard = DashboardWindow.INSTANCE;
        addMember = AddMemberWindow.INSTANCE;
        addBookCopy = AddBookCopyWindow.INSTANCE;
        allMemberIds = AllMemberIdsWindow.INSTANCE;
        allBook = AllBookIdsWindow.INSTANCE;
        checkOutBook = CheckoutFormWindow.INSTANCE;
        checkoutRecord = TableExample.INSTANCE;

        rightSidePanel.add(listMenu[0], dashboard);
        rightSidePanel.add(listMenu[1], addMember);
        rightSidePanel.add(listMenu[2], allMemberIds);
        rightSidePanel.add(listMenu[3], addBookCopy);
        rightSidePanel.add(listMenu[4], allBook);
        rightSidePanel.add(listMenu[5], checkOutBook);
        rightSidePanel.add(listMenu[6], checkoutRecord);
    }

    public void goToDashBoard() {
        ((CardLayout) (rightSidePanel.getLayout())).show(rightSidePanel, listMenu[0]);
    }
    public void goToCheckoutRecord() {
        ((CardLayout) (rightSidePanel.getLayout())).show(rightSidePanel, listMenu[6]);
        sideBarMenuList.setSelectedIndex(6);
    }
    private void initComponents() {
        setRightAppSidePanel();
        setLeftAppSidePanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSidePanel, rightSidePanel);
        splitPane.setDividerLocation(150);
        add(splitPane, BorderLayout.CENTER);
    }
}
