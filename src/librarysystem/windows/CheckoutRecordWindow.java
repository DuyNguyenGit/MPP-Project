package librarysystem.windows;

import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.LibrarySystem;
import librarysystem.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CheckoutRecordWindow extends JPanel implements LibWindow {
    public static final CheckoutRecordWindow INSTANCE = new CheckoutRecordWindow();
    SystemController ci = new SystemController();

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;
    private JTextField memberIDText;
    private JLabel errorLabel;
    private JTable recordsTable;

    private boolean isInitialized = false;

    private CheckoutRecordWindow() {
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
//		mainPanel = new JPanel();
//		mainPanel.setLayout(null);
//		
//		constructComponents();
//		setBounds();
//		addComponents();
//		
//		
////    		Add listeners
//		addLibraryMemberListener(addMemberButton);
//		add(mainPanel);
//		isInitialized(true);
//		setSize(621, 450);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Checkout Records");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4)); // set the layout to horizontal flow
        JLabel memberID = new JLabel("Member ID");
        memberIDText = new JTextField(11);
        JButton checkoutButton = new JButton("Show Check Out Record");
        SystemController sc = new SystemController();
        checkoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = memberIDText.getText();
                try {
                    showMemberCheckRecord(memberID);
                } catch (LibrarySystemException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(CheckoutRecordWindow.this,
                            e1.getMessage());
                }
            }
        });
        middlePanel.add(memberID);
        middlePanel.add(memberIDText);
        middlePanel.add(checkoutButton);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        middlePanel.add(errorLabel);
        JScrollPane scrollPane = new JScrollPane();
        recordsTable = new JTable();
        scrollPane.setViewportView(recordsTable);
        middlePanel.add(scrollPane);
    }

    private void showMemberCheckRecord(String memberID) throws LibrarySystemException {
        // Use JTable to render member’s checkout records
        SystemController sc = new SystemController();
        CheckoutRecord checkoutRecord;
        try {
            checkoutRecord = sc.getMemberCheckoutRecord(memberID);
            errorLabel.setText("");
            List<CheckoutRecordEntry> entries = checkoutRecord.getCheckoutRecordEntries();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ISBN");
            model.addColumn("Title");
            model.addColumn("Checkout Date");
            model.addColumn("Due Date");
            if (entries != null) {
                for (CheckoutRecordEntry entry : entries) {
                    String isbn = entry.getCopy().getBook().getIsbn();
                    String title = entry.getCopy().getBook().getTitle();
                    String checkoutdate = entry.getCheckoutDate().toString();
                    String duedate = entry.getDueDate().toString();
                    String[] row = {isbn, title, checkoutdate, duedate};
                    model.addRow(row);
                    String format = String.format("%s\t%s\t%s\t%s", isbn, title, checkoutdate, duedate);
                    System.out.println(format);
                }
            }
            recordsTable.setModel(model);
        } catch (Exception e) {
//			errorLabel.setText(e.getMessage());
            JOptionPane.showMessageDialog(CheckoutRecordWindow.this,
                    e.getMessage());
        }

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
