package librarysystem.windows;

import controller.ControllerInterface;
import controller.SystemController;
import librarysystem.LibWindow;
import utils.Util;

import javax.swing.*;
import java.awt.*;


public class AllBookIdsWindow extends JPanel implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;


    //Singleton class
    private AllBookIdsWindow() {
        super(new BorderLayout());
        init();
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
//        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        add(mainPanel);
        isInitialized(true);
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
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        textArea = new TextArea(8, 20);
        //populateTextArea();
        middlePanel.add(textArea);

    }


    public void setData(String data) {
        textArea.setText(data);
    }

//	private void populateTextArea() {
//		//populate
//		List<String> ids = ci.allBookIds();
//		Collections.sort(ids);
//		StringBuilder sb = new StringBuilder();
//		for(String s: ids) {
//			sb.append(s + "\n");
//		}
//		textArea.setText(sb.toString());
//	}

    @Override
    public boolean isInitialized() {
        // TODO Auto-generated method stub
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }
}
