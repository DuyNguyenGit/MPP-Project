package librarysystem.table;

import business.CheckoutRecord;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TableExample extends JPanel implements LibWindow {
	public static final TableExample INSTANCE = new TableExample();
	private JPanel topPanel; //panel containing table
	private JPanel middlePanel;
	private JTable table;
	private JScrollPane scrollPane;
	private CustomTableModel model;
	private JLabel label;
	private static List<CheckoutRecord> checkoutRecord;
	private SystemController systemController;
	
	//table data and config
	private final String[] DEFAULT_COLUMN_HEADERS 
	   = {"Member Id", "First Name", "Last Name", "ISBN", "Title", " Checkout Date", "Due Date"};
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 480;
	private static final int TABLE_WIDTH = (int) (0.75 * FRAME_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * FRAME_HEIGHT);

    //these numbers specify relative widths of the columns -- 
    //they  must add up to 1
    private final float [] COL_WIDTH_PROPORTIONS =
    	{0.14f, 0.14f, 0.14f, 0.12f, 0.15f, 0.17f, 0.14f};//{.75f, .25f}
    
	public TableExample() {
		initializeWindow();
		JPanel mainPanel = new JPanel();
		defineTopPanel();
		defineMiddlePanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		add(mainPanel);
		loadCheckoutRecord();
	}
	public void loadCheckoutRecord() {
		systemController = new SystemController();
		this.checkoutRecord = systemController.loadCheckoutRecord();
		setTableValues();
		getTable().updateUI();
		setVisible(true);
	}

    public JTable getTable() {
        return table;
    }

    public CustomTableModel getModel() {
        return model;
    }

	private void initializeWindow() {
		JLabel checkoutRecordTitle = new JLabel("Checkout Record");
		Util.adjustLabelFont(checkoutRecordTitle, Color.BLUE.darker(), true);
		add(checkoutRecordTitle, BorderLayout.NORTH);
		centerFrameOnDesktop(this);
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
	}
	
	public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = FRAME_HEIGHT;
        int frameWidth = FRAME_WIDTH;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 2);
    }

    private void defineTopPanel() {
        topPanel = new JPanel();
        createTableAndTablePane();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(scrollPane);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        label = new JLabel("Checkout successfully!");
        middlePanel.add(label);

    }

    private void createTableAndTablePane() {
        updateModel();
        table = new JTable(model);
        createCustomColumns(table, TABLE_WIDTH,
                COL_WIDTH_PROPORTIONS, DEFAULT_COLUMN_HEADERS);
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(
                new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        scrollPane.getViewport().add(table);
    }

    private void updateModel() {
        List<String[]> list = new ArrayList<String[]>();
        if (model == null) {
            model = new CustomTableModel();
        }
        model.setTableValues(list);
    }

    private void createCustomColumns(JTable table, int width, float[] proportions,
                                     String[] headers) {
        table.setAutoCreateColumnsFromModel(false);
        int num = headers.length;
        for (int i = 0; i < num; ++i) {
            TableColumn column = new TableColumn(i);
            column.setHeaderValue(headers[i]);
            column.setMinWidth(Math.round(proportions[i] * width));
            table.addColumn(column);
        }
    }

    //	class ButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			setValues(model);
//			table.updateUI();
//		}
//	}
	public void setTableValues() {
		List<String[]> data = new ArrayList<>();
		for (CheckoutRecord c : this.checkoutRecord) {
			String memberId = c.getLibraryMember().getMemberId();
			String firstName = c.getLibraryMember().getFirstName();
			String lastName = c.getLibraryMember().getLastName();
			String isbn = c.getCheckoutRecordEntryList().get(0).getBookNum().getBook().getIsbn();
			String title = c.getCheckoutRecordEntryList().get(0).getBookNum().getBook().getTitle();
			LocalDate checkoutDate = c.getCheckoutRecordEntryList().get(0).getCheckOutDate();
			LocalDate dueDate = c.getCheckoutRecordEntryList().get(0).getDueDate();

            String[] row = {memberId, firstName, lastName, isbn, title, checkoutDate.toString(), dueDate.toString()};

			data.add(row);
		}

		model.resetTableValues();
		model.setTableValues(data);	
	}
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable()
//		{
//			public void run() {
//				TableExample mf = new TableExample();
//				mf.setVisible(true);
//			}
//		});
//	}
	
	private static final long serialVersionUID = 3618976789175941431L;

	@Override
	public void init() {

	}

	@Override
	public boolean isInitialized() {
		return false;
	}

	@Override
	public void isInitialized(boolean val) {

	}

    private static final long serialVersionUID = 3618976789175941431L;
}
