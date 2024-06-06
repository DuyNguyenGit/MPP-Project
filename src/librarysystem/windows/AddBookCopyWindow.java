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
    private TextField isbnTxtField;
    private JButton addBookCopyBtn;
    private JTable bookCopyTable;

    private String[] columnNames = {"No", "ISBN", "Title", "Copy Num", "Available"};

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
        defineMainPanel();
        add(mainPanel);
        isInitialized(true);
        setSize(621, 450);
    }

    public void defineMainPanel() {
        mainPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        mainPanel.setLayout(fl);
        isbnTxtField = new TextField("", 20);
        mainPanel.add(isbnTxtField);

        addBookCopyBtn = new JButton("Add Book Copy");
        addBookCopyButtonListener(addBookCopyBtn);
        mainPanel.add(addBookCopyBtn);
        defineBookCopyTable();
    }

    private void defineBookCopyTable() {
        DefaultTableModel defaultTableModel = new DefaultTableModel(columnNames, 0);
        bookCopyTable = new JTable(defaultTableModel);
        bookCopyTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        bookCopyTable.setEnabled(false);
        bookCopyTable.setBounds(30, 40, 200, 200);

        JScrollPane sp = new JScrollPane(bookCopyTable);
        sp.setPreferredSize(new Dimension(480, 300));
        mainPanel.add(sp);
    }

    private void addBookCopyButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            try {
                Book book = ci.addNewBookCopy(getIsbnValue());
                DefaultTableModel defaultTableModel = (DefaultTableModel) bookCopyTable.getModel();
                defaultTableModel.getDataVector().removeAllElements();

                int no = 0;
                for (int i = book.getCopies().length - 1; i >= 0; i--) {
                    no++;
                    BookCopy bookCopy = book.getCopies()[i];
                    Object[] rowData = new Object[]{no, book.getIsbn(), book.getTitle(), bookCopy.getCopyNum(), bookCopy.isAvailable()};
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
