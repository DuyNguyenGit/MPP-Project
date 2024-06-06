package librarysystem.windows;

//package librarysystem;

import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.Util;

import javax.swing.*;
import java.awt.*;


public class AddNewLibraryMemberWindow extends JPanel implements LibWindow {
    public static final AddNewLibraryMemberWindow INSTANCE = new AddNewLibraryMemberWindow();
    SystemController ci = new SystemController();
    
	private boolean isInitialized = false;
	
	private JPanel mainPanel;
    private JTextField firstNameTextField;
    private JLabel firstnameLabel;
    private JTextField lastNameField;
    private JLabel lastNameLabel;
    private JTextField memberIdField;
    private JLabel memberIdLabel;
    private JTextField telephoneField;
    private JLabel telephoneLabel;
    private JLabel addLibraryMemberHeadingLabel;
    private JButton addMemberButton;
    private JTextField streetTextField;
    private JTextField cityTextField;
    private JLabel addressLabel;
    private JLabel streetLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JTextField stateTextField;
    private JTextField zipcodeTextField;
    private JLabel zipCodeLabel;
    
	
	
	
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
        firstNameTextField = new JTextField (8);
        firstnameLabel = new JLabel ("First Name");
        lastNameField = new JTextField (5);
        lastNameLabel = new JLabel ("Last Name");
        memberIdField = new JTextField (5);
        memberIdLabel = new JLabel ("Member Id");
        telephoneField = new JTextField (5);
        telephoneLabel = new JLabel ("Telephone");
        addLibraryMemberHeadingLabel = new JLabel ("Add Library Member");
        Util.adjustLabelFont(addLibraryMemberHeadingLabel, Color.BLUE.darker(), true);
        addMemberButton = new JButton ("Add member");
        streetTextField = new JTextField (5);
        cityTextField = new JTextField (5);
        addressLabel = new JLabel ("Address");
        streetLabel = new JLabel ("Street");
        cityLabel = new JLabel ("City");
        stateLabel = new JLabel ("State");
        stateTextField = new JTextField (5);
        zipcodeTextField = new JTextField (5);
        zipCodeLabel = new JLabel ("Zipcode");
		
	}
	
	private void addComponents() {
        add (firstNameTextField);
        add (firstnameLabel);
        add (lastNameField);
        add (lastNameLabel);
        add (memberIdField);
        add (memberIdLabel);
        add (telephoneField);
        add (telephoneLabel);
        add (addLibraryMemberHeadingLabel);
        add (addMemberButton);
        add (streetTextField);
        add (cityTextField);
        add (addressLabel);
        add (streetLabel);
        add (cityLabel);
        add (stateLabel);
        add (stateTextField);
        add (zipcodeTextField);
        add (zipCodeLabel);
    }
	
	private void setBounds() {
        //set component bounds (only needed by Absolute Positioning)
        firstNameTextField.setBounds (125, 110, 145, 25);
        firstnameLabel.setBounds (45, 110, 65, 25);
        lastNameField.setBounds (390, 110, 140, 25);
        lastNameLabel.setBounds (315, 110, 100, 25);
        memberIdField.setBounds (125, 165, 145, 25);
        memberIdLabel.setBounds (45, 165, 100, 25);
        telephoneField.setBounds (390, 170, 145, 25);
        telephoneLabel.setBounds (315, 165, 100, 25);
        addLibraryMemberHeadingLabel.setBounds (250, 30, 155, 50);
        addMemberButton.setBounds (270, 355, 115, 30);
        streetTextField.setBounds (125, 260, 145, 25);
        cityTextField.setBounds (390, 260, 145, 25);
        addressLabel.setBounds (270, 215, 100, 25);
        streetLabel.setBounds (50, 260, 100, 25);
        cityLabel.setBounds (325, 255, 100, 25);
        stateLabel.setBounds (50, 310, 100, 25);
        stateTextField.setBounds (125, 310, 145, 25);
        zipcodeTextField.setBounds (390, 315, 150, 25);
        zipCodeLabel.setBounds (320, 315, 100, 25);
       
        
	}
	
	
	/* This class is a singleton */
    private AddNewLibraryMemberWindow () {
    	super(new BorderLayout());
    	init();
    	}
    
	    public void init() {     		
	    		mainPanel = new JPanel();
	    		mainPanel.setLayout(null);
	    		
	    		constructComponents();
	    		setBounds();
	    		addComponents();
	    		
	    		
	//    		Add listeners
	    		addLibraryMemberListener(addMemberButton);
	    		add(mainPanel);
	    		isInitialized(true);
	    		setSize(621, 450);
	    }

	    private void clearFields() {
	    	firstNameTextField.setText("");
	    	lastNameField.setText("");
	    	memberIdField.setText("");
	    	telephoneField.setText("");
	    	streetTextField.setText("");
	    	cityTextField.setText("");
	    	stateTextField.setText("");
	    	zipcodeTextField.setText("");
	    }
	
    	
        
    	private void addLibraryMemberListener(JButton butn) {
    		butn.addActionListener(evt -> {
    			try {
					ci.addNewLibraryMember(firstNameTextField.getText(), lastNameField.getText(), memberIdField.getText(), telephoneField.getText(),streetTextField.getText(), cityTextField.getText(), stateTextField.getText(), zipcodeTextField.getText());
					JOptionPane.showMessageDialog(AddNewLibraryMemberWindow.this,"New member added successfully");
					AllMemberIdsWindow.INSTANCE.reloadMember();
					clearFields();
    			} catch (LibrarySystemException e) {
					JOptionPane.showMessageDialog(AddNewLibraryMemberWindow.this,e.getMessage());
				}

    			
    		});
    	}
    	
    
}
