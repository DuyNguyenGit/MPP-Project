package librarysystem.windows;

import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AddBookWindow extends JPanel implements LibWindow {

    public static final AddBookWindow INSTANCE = new AddBookWindow();
    SystemController ci = new SystemController();

    private boolean isInitialized = false;

    DefaultListModel authorListModel;
    private JPanel mainPanel;
    private JTextField isbnTextField;
    private JLabel isbnLabel;
    private JTextField titleField;
    private JLabel titleLabel;
    private JTextField checkoutDayDurationField;
    private JLabel checkoutDayDurationLabel;
    private JComboBox checkoutDay;
    private JLabel checkoutDayLabel;
    private JTextField authorField;
    private JLabel authorLabel;
    private JLabel addLibraryMemberHeadingLabel;
    private JButton addBookButton;
    private JTextField copiesUnitTextField;
    private JTextField cityTextField;
    private JLabel addressLabel;
    private JLabel copiesUnitLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JTextField stateTextField;
    private JTextField zipcodeTextField;
    private JLabel zipCodeLabel;
    private JButton addAuthorButton;
    private JList authorList;
    private JScrollPane authorListScroller;

    private AddBookWindow() {
        super(new BorderLayout());
        init();
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        constructComponents();
        setBounds();
        addComponents();
        addEventListeners();


//    		Add listeners
//		addBookListener(addBookButton);
        add(mainPanel);
        isInitialized(true);
        setSize(621, 450);
    }

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

    private void constructComponents() {
        //construct components
        isbnTextField = new JTextField(8);
        isbnLabel = new JLabel("ISBN");
        titleField = new JTextField(5);
        titleLabel = new JLabel("Title");
        checkoutDayDurationField = new JTextField(5);
        checkoutDayDurationLabel = new JLabel("Borrow Days");
        checkoutDay = new JComboBox();
        checkoutDayLabel = new JLabel("Borrow Days");
        checkoutDay.setModel(new DefaultComboBoxModel(new String[]{"7", "21"}));
        authorField = new JTextField(5);
        authorLabel = new JLabel("Author");
        addLibraryMemberHeadingLabel = new JLabel("Add Book");
        Util.adjustLabelFont(addLibraryMemberHeadingLabel, Color.BLUE.darker(), true);
        addBookButton = new JButton("Add book");
        copiesUnitTextField = new JTextField(5);
        cityTextField = new JTextField(5);
        addressLabel = new JLabel("Address");
        copiesUnitLabel = new JLabel("Copies Unit");
        cityLabel = new JLabel("City");
        stateLabel = new JLabel("State");
        stateTextField = new JTextField(5);
        zipcodeTextField = new JTextField(5);
        zipCodeLabel = new JLabel("Zipcode");
        addAuthorButton = new JButton("+");
        authorListModel = new DefaultListModel();
        authorList = new JList(authorListModel);
        authorListScroller = new JScrollPane();
        authorListScroller.setViewportView(authorList);
        authorList.setLayoutOrientation(JList.VERTICAL);
    }

    private void addComponents() {
        add(isbnTextField);
        add(isbnLabel);
        add(titleField);
        add(titleLabel);
        add(checkoutDayDurationField);
        add(checkoutDayDurationLabel);
        add(checkoutDay);
        add(checkoutDayLabel);
        add(authorField);
        add(authorLabel);
        add(addLibraryMemberHeadingLabel);
        add(addBookButton);
        add(copiesUnitTextField);
//        add (cityTextField);
//        add (addressLabel);
        add(copiesUnitLabel);
//        add (cityLabel);
//        add (stateLabel);
//        add (stateTextField);
//        add (zipcodeTextField);
//        add (zipCodeLabel);
        add(addAuthorButton);
        add(authorList);
        add(authorListScroller);
    }

    private void setBounds() {
        //set component bounds (only needed by Absolute Positioning)
        isbnTextField.setBounds(125, 110, 145, 25);
        isbnLabel.setBounds(45, 110, 65, 25);
        titleField.setBounds(390, 110, 140, 25);
        titleLabel.setBounds(315, 110, 100, 25);
//        checkoutDayDurationField.setBounds (125, 165, 145, 25);
//        checkoutDayDurationLabel.setBounds (45, 165, 100, 25);
        checkoutDay.setBounds(125, 165, 145, 25);
        checkoutDayLabel.setBounds(45, 165, 100, 25);
        authorField.setBounds(390, 170, 145, 25);
        authorLabel.setBounds(315, 165, 100, 25);
        addLibraryMemberHeadingLabel.setBounds(250, 30, 155, 50);
        addBookButton.setBounds(270, 355, 115, 30);

        cityTextField.setBounds(390, 260, 145, 25);
//        addressLabel.setBounds (270, 215, 100, 25);
        copiesUnitTextField.setBounds(125, 195, 145, 50);
        copiesUnitLabel.setBounds(50, 200, 100, 25);
//        cityLabel.setBounds (325, 255, 100, 25);
//        stateLabel.setBounds (50, 310, 100, 25);
//        stateTextField.setBounds (125, 310, 145, 25);
//        zipcodeTextField.setBounds (390, 315, 150, 25);
//        zipCodeLabel.setBounds (320, 315, 100, 25);
//        addAuthorButton.setBounds (270, 215, 45, 30);
        authorList.setBounds(200, 250, 185, 50);
        authorListScroller.setBounds(390, 200, 145, 50);

    }


    private void addEventListeners() {
        authorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authorListModel.addElement(authorField.getText());
                    authorField.setText("");
                }
            }

        });

        addBookButton.addActionListener(e -> {
            // TODO Auto-generated method stub
            List<String> authorArrayList = new ArrayList<>();
            for (int i = 0; i < authorList.getModel().getSize(); i++) {
                authorArrayList.add((String) authorList.getModel().getElementAt(i));
            }

            try {
                ci.addBook(isbnTextField.getText(), titleField.getText(), authorArrayList, checkoutDay.getSelectedItem().toString(), copiesUnitTextField.getText());
                clearFields();
                AllBookIdsWindow.INSTANCE.reloadBooks();
                JOptionPane.showMessageDialog(this, "Book has been added successfully");
            } catch (LibrarySystemException e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
        });
    }

    private void clearFields() {
        isbnTextField.setText("");
        titleField.setText("");
        checkoutDayDurationField.setText("");
        DefaultListModel listModel = (DefaultListModel) authorList.getModel();
        listModel.removeAllElements();
        copiesUnitTextField.setText("");
//		cityTextField.setText("");
//		stateTextField.setText("");
//		zipcodeTextField.setText("");
    }


}
