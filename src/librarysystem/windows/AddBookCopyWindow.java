package librarysystem.windows;

import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.LibrarySystem;
import librarysystem.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookCopyWindow extends JPanel implements LibWindow {
    JLabel text;
    public static AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    SystemController ci = new SystemController();
    private boolean isInitialized = false;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;
    private JTextField isbnIDText;
    //	private JTextField isbnText;
    private JLabel errorLabel;
    private JTextField totalCopyField;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private AddBookCopyWindow() {
        super(new BorderLayout());
        init();
    }

    ;

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
        JLabel AllIDsLabel = new JLabel("Add Book Copy");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4)); // set the layout to horizontal flow

        JLabel isbn = new JLabel("ISBN");
        isbnIDText = new JTextField(11);


        JLabel numOfCopies = new JLabel("Total Copies");
        totalCopyField = new JTextField(11);


        JButton addbookCopyButton = new JButton("Add Copy");
        SystemController sc = new SystemController();
        addbookCopyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnIDText.getText().trim();
                String numCopiesStr = totalCopyField.getText().trim();
                int numCopies;
                if (isbn.equals("") || numCopiesStr.equals("")) {
//					errorLabel.setText("Fields cannot be empty!");
                    JOptionPane.showMessageDialog(AddBookCopyWindow.INSTANCE, "Fields cannot be empty!");
                    return;
                }
                try {
                    numCopies = Integer.parseInt(numCopiesStr);
                    if (numCopies <= 0) {
                        JOptionPane.showMessageDialog(AddBookCopyWindow.INSTANCE, "Number of copies should be greater than 0");
                        return;
                    }
                } catch (NumberFormatException ex) {
//					errorLabel.setText("Number of copies must be an integer");
                    JOptionPane.showMessageDialog(AddBookCopyWindow.INSTANCE, "Number of copies must be an integer");
                    return;
                }
                try {
                    sc.addBookCopy(isbn, numCopies);
                    clearFields();
                    JOptionPane.showMessageDialog(AddBookCopyWindow.INSTANCE, "Added book copy successfully!");
                    clearFields();
                    AllBookIdsWindow.INSTANCE.reloadBooks();
//					errorLabel.setText("add book copy successfully");
                } catch (LibrarySystemException lse) {
//					errorLabel.setText(lse.getMessage());
                    JOptionPane.showMessageDialog(AddBookCopyWindow.INSTANCE, lse.getMessage());
                }
            }
        });
        middlePanel.add(isbn);
        middlePanel.add(isbnIDText);

        middlePanel.add(numOfCopies);
        middlePanel.add(totalCopyField);

        middlePanel.add(addbookCopyButton); // add the button to the right of the ISBN text field

        errorLabel = new JLabel(); // create a label for error messages
        errorLabel.setForeground(Color.RED); // set the color to red
        middlePanel.add(errorLabel); // add the error label to the middle panel
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

    private void clearFields() {
        isbnIDText.setText("");
        totalCopyField.setText("");
    }

}
