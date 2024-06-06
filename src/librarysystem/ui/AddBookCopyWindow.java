package librarysystem.ui;

import business.Book;
import business.BookCopy;
import controller.ControllerInterface;
import controller.SystemController;
import exception.LibrarySystemException;
import utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddBookCopyWindow extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextField isbnTxtField;
    private JButton addBookCopyBtn;
    private JTable bookCopyTable;

    //Singleton class
    private AddBookCopyWindow() {
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
        getContentPane().add(mainPanel);
        isInitialized = true;
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Add new Book Copy");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        isbnTxtField = new TextField("", 20);
        middlePanel.add(isbnTxtField);

        addBookCopyBtn = new JButton("Add Book Copy");
        addBookCopyButtonListener(addBookCopyBtn);
        middlePanel.add(addBookCopyBtn);

        defineBookCopyTable();
    }

    private void defineBookCopyTable() {
        String[] columnNames = {"ISBN", "Title", "Copy Num", "Available"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(columnNames, 0);
        bookCopyTable = new JTable(defaultTableModel);
        middlePanel.add(bookCopyTable);
    }

    public void defineLowerPanel() {
        JButton backToMainButn = new JButton("Back to Main");
        backToMainButn.addActionListener(new BackToMainListener());
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        lowerPanel.add(backToMainButn);
    }

    private void addBookCopyButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            try {
                Book book = ci.addNewBookCopy(getIsbnValue());
                DefaultTableModel defaultTableModel = (DefaultTableModel) bookCopyTable.getModel();
                defaultTableModel.getDataVector().removeAllElements();

                for (int i = 0; i < book.getCopies().length; i++) {
                    BookCopy bookCopy = book.getCopies()[i];
                    Object[] rowData = new Object[]{book.getIsbn(), book.getTitle(), bookCopy.getCopyNum(), bookCopy.isAvailable()};
                    defaultTableModel.addRow(rowData);
                }
                bookCopyTable.setModel(defaultTableModel);
                bookCopyTable.updateUI();
            } catch (LibrarySystemException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });
    }

    class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            resetForm();
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    private String getIsbnValue() {
        return isbnTxtField.getText();
    }

    private void resetForm() {
        isbnTxtField.setText("");
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
