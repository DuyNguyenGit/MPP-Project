package librarysystem.windows;

import business.CheckoutRecord;
import controller.ControllerInterface;
import controller.SystemController;
import librarysystem.LibWindow;
import librarysystem.LibrarySystem;
import librarysystem.Util;
import librarysystem.app_main.AppPanel;
import librarysystem.table.TableExample;
import utils.Util;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckoutFormWindow extends JPanel implements LibWindow {
    public static final CheckoutFormWindow INSTANCE = new CheckoutFormWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;
    private JPanel container;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JTextField memberIdField;
    private JTextField isbnField;
    private JLabel label;
    private JButton searchButton;
    private JButton logoutButton;


    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private JTextField messageBar = new JTextField();

    public void clear() {
        messageBar.setText("");
    }

    /* This class is a singleton */
    private CheckoutFormWindow() {
        init();
    }

    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        defineLowerHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(lowerHalf, BorderLayout.SOUTH);
        add(mainPanel);
        isInitialized(true);
//        pack();
        //setSize(660, 500);


    }

    private void defineUpperHalf() {

        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
        upperHalf.add(lowerPanel, BorderLayout.SOUTH);

    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        //middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
        middleHalf.add(s, BorderLayout.SOUTH);

    }

    private void defineLowerHalf() {

        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("Back to Main");
        addBackButtonListener(backButton);
        lowerHalf.add(backButton);

    }

    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Checkout Form");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);

    }


    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        searchButton = new JButton("Checkout");
        addSearchButtonListener(searchButton);
        lowerPanel.add(searchButton);
    }

    private void defineLeftTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        memberIdField = new JTextField(10);
        label = new JLabel("Member Id");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(memberIdField);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText, BorderLayout.NORTH);
        leftTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void defineRightTextPanel() {

        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        isbnField = new JTextField(10);
        label = new JLabel("ISBN");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(isbnField);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText, BorderLayout.NORTH);
        rightTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addSearchButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            String memberId = memberIdField.getText();
            String isbn = isbnField.getText();
            ControllerInterface ci = new SystemController();
            try {
                ci.checkoutForm(memberId,isbn);
                LibrarySystem.hideAllWindows();
                TableExample.INSTANCE.loadCheckoutRecord();
                AppPanel.INSTANCE.goToCheckoutRecord();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });
    }
}