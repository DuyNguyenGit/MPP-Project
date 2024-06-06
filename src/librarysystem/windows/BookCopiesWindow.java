package librarysystem.windows;

import business.*;
import librarysystem.LibWindow;
import librarysystem.LibrarySystem;
import librarysystem.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BookCopiesWindow extends JPanel implements LibWindow {
	public static final BookCopiesWindow INSTANCE = new BookCopiesWindow();
	SystemController ci = new SystemController();

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;
	private JTextField ISBNText;
	private JLabel errorLabel;
	private JTable recordsTable;

	private boolean isInitialized = false;

	private BookCopiesWindow() {
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

	private void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Book Copies");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}

	private void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4)); // set the layout to horizontal flow
		JLabel ISBN = new JLabel("ISBN");
		ISBNText = new JTextField(11);
		JButton showCopiesButton = new JButton("Show Book Copies");
		SystemController sc = new SystemController();
		showCopiesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ISBN = ISBNText.getText();
				try {
					showBookCopiesRecord(ISBN);
				} catch (LibrarySystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		middlePanel.add(ISBN);
		middlePanel.add(ISBNText);
		middlePanel.add(showCopiesButton);

		errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);
		middlePanel.add(errorLabel);
		JScrollPane scrollPane = new JScrollPane();
		recordsTable = new JTable();
		scrollPane.setViewportView(recordsTable);
		recordsTable.setEnabled(false);
		middlePanel.add(scrollPane);
	}

	private void showBookCopiesRecord(String ISBN) throws LibrarySystemException {
		// Use JTable to render member’s checkout records
		
		SystemController sc = new SystemController();
		List<BookCopy> bookCopyList = null;
		
		try {
			bookCopyList = new ArrayList(Arrays.asList(sc.getBookCopyArray(ISBN)));
		} catch(LibrarySystemException e) {
			
			JOptionPane.showMessageDialog(this,e.getMessage());
			
		}
//		CheckoutRecord checkoutRecord = sc.getMemberCheckoutRecord(ISBN);
//		List<CheckoutRecordEntry> entries = checkoutRecord.getCheckoutRecordEntries();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ISBN");
		model.addColumn("Title");
		model.addColumn("Copy Number");
		model.addColumn("Obtained By");
		model.addColumn("Duedate");
		model.addColumn("Overdue");
		if (bookCopyList != null) {
			for (BookCopy entry : bookCopyList) {
				try {
				String isbn = ISBN;
				String checkoutdate="N/A",duedate="N/A",overdue="N/A",obtainedBy="N/A";
				String title = entry.getBook().getTitle();
				String copyNumber = Integer.toString(entry.getCopyNum()); 
				
//				Obtained by
				LibraryMember libraryMemberOfBookCopy = entry.getLibraryMemberOfBookCopy();
				obtainedBy = libraryMemberOfBookCopy != null ? libraryMemberOfBookCopy.getMemberId() : "";
				
//				Finding entry for that book copy from library member
				CheckoutRecord checkoutRecord = libraryMemberOfBookCopy != null ? sc.getMemberCheckoutRecord(libraryMemberOfBookCopy.getMemberId()) : null;
				if(checkoutRecord != null) {
					CheckoutRecordEntry checkoutEntry = checkoutRecord.getEntryByBookCopy(entry);
					checkoutdate = checkoutEntry.getCheckoutDate().toString();
					duedate = checkoutEntry.getDueDate().toString();
					if (new SimpleDateFormat("yyyy-MM-dd").parse(duedate).before(new Date())) {
					    overdue= "Yes";
					} else {
						overdue="No";
					}
				}

				String[] row = { isbn, title,copyNumber,obtainedBy, duedate,overdue };
				model.addRow(row);
				String format = String.format("%s\t%s\t%s\t%s", isbn, title, checkoutdate, duedate);
				System.out.println(format);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(this,e.getMessage());
				}
				

			}
		}
		recordsTable.setModel(model);
	}

	private void defineLowerPanel() {
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
