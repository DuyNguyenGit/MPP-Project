package librarysystem.windows;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.LibrarySystem;
import librarysystem.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CheckOutBookWindow extends JPanel implements LibWindow {
    public static final CheckOutBookWindow INSTANCE = new CheckOutBookWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;
    private JTextField memberIDText;
    private JTextField isbnText;
//	private JLabel resultLabel;


    private CheckOutBookWindow() {
        super(new CardLayout());
        init();
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        add(mainPanel);
        isInitialized = true;
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Check Out Books");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
        JLabel memberID = new JLabel("Member ID");
        JLabel isbn = new JLabel("ISBN");
        memberIDText = new JTextField(11);
        isbnText = new JTextField(11);
        JButton checkoutButton = new JButton("CheckOut");
        SystemController sc = new SystemController();
        checkoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = memberIDText.getText();
                String isbnString = isbnText.getText();
                try {
                    sc.checkOutBook(memberID, isbnString);
//					resultLabel.setText("");
                    JOptionPane.showMessageDialog(CheckOutBookWindow.this,
                            "Checkout successful!");
//					resultLabel.setText(String.format("Member %s has checked out book: %s", memberID, sc.getBookName(isbnString)));
                    clearJTextFields();
                } catch (LibrarySystemException lse) {
//					errorLabel.setText(lse.getMessage());
                    JOptionPane.showMessageDialog(CheckOutBookWindow.this,
                            lse.getMessage());
                }


            }
        });
        middlePanel.add(memberID);
        middlePanel.add(memberIDText);
        middlePanel.add(isbn);
        middlePanel.add(isbnText);
        middlePanel.add(checkoutButton);

//	    resultLabel = new JLabel(); 
//	    resultLabel.setForeground(Color.BLUE); 
//	    middlePanel.add(resultLabel); 
    }


    protected void clearJTextFields() {
        memberIDText.setText("");
        isbnText.setText("");
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        addBackButtonListener(backButton);
//		lowerPanel.add(backButton);
    }

    public void setData(String data) {
        textArea.setText(data);
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
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


