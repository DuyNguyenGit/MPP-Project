package librarysystem.windows;

import business.Book;
import business.BookCopy;
import controller.ControllerInterface;
import controller.SystemController;
import exception.LibrarySystemException;
import librarysystem.LibWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class AddBookCopyWindow extends JPanel implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static final AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel middlePanel;
    private TextField isbnTxtField;
    private JButton addBookCopyBtn;
    private JTable bookCopyTable;

    //Singleton class
    private AddBookCopyWindow() {
        super(new BorderLayout());
        init();
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        defineMiddlePanel();
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        add(mainPanel);
        isInitialized(true);
        setSize(621, 450);
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

    private String getIsbnValue() {
        return isbnTxtField.getText();
    }

    private void clearFields() {
        isbnTxtField.setText("");
    }

}
