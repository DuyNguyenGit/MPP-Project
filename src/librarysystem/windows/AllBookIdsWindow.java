package librarysystem.windows;

import controller.ControllerInterface;
import controller.SystemController;
import librarysystem.LibWindow;
import utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class AllBookIdsWindow extends JPanel implements LibWindow {
    public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private String[] columnNames = {"N", "Isbn", "Title", "Authors", "NoOfCopy"};
    private JTable table;

    private AllBookIdsWindow() {
        super(new BorderLayout());
        init();
    }


    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("All Book IDs");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        String[][] data = SystemController.allBooks();

        table = new JTable(new DefaultTableModel(data, columnNames));
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.setEnabled(false);
        table.setBounds(30, 40, 200, 200);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(table);
        middlePanel.add(sp);
    }

    public void reloadBook() {
        String[][] data = SystemController.allBooks();
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        // Clear All
        for (int i = 0; i < data.length; i++) {
        }
        for (int i = table.getRowCount() - 1; i > -1; i--) {
            defaultTableModel.removeRow(i);
        }
        // Add All
        for (int i = 0; i < data.length; i++) {
            defaultTableModel.insertRow(i, data[i]);
        }
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        lowerPanel.setVisible(false);
    }

    @Override
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
        isInitialized(true);
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
