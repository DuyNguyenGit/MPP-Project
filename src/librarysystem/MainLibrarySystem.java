package librarysystem;

import librarysystem.app_main.AppPanel;
import librarysystem.login_screen.LoginPanel;

import javax.swing.*;
import java.awt.*;


public class MainLibrarySystem extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final MainLibrarySystem mainLibrarySystem = new MainLibrarySystem();
    private JPanel mainPanel;
    private final String[] listPanel = {"login", "app"};

    private MainLibrarySystem() {
        initComponents();
        setVisible(true);
        Util.centerFrameOnDesktop(this);
    }

    private void initComponents() {
        initLayouts();
        initAppInfo();
    }

    private void initAppInfo() {
        setTitle("Library Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 500);
    }

    private void initLayouts() {
        mainPanel = new JPanel(new CardLayout());
        LoginPanel loginPanel = LoginPanel.INSTANCE;
        AppPanel appPanel = AppPanel.INSTANCE;
        appPanel.setBackground(Color.blue);

        mainPanel.add(listPanel[0], loginPanel);
        mainPanel.add(listPanel[1], appPanel);

        add(mainPanel);
    }

    public void navigateToLogin() {
        ((CardLayout) (mainPanel.getLayout())).show(mainPanel, listPanel[0]);
    }

    public void navigateToApp() {
        ((CardLayout) (mainPanel.getLayout())).show(mainPanel, listPanel[1]);
    }

    public static MainLibrarySystem getInstance() {
        return mainLibrarySystem;
    }
}
