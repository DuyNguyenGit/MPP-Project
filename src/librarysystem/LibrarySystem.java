package librarysystem;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.windows.AddNewLibraryMemberWindow;
import librarysystem.windows.AllBookIdsWindow;
import librarysystem.windows.AllMemberIdsWindow;
import librarysystem.windows.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;


public class LibrarySystem extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, addNewMember;
    String pathToImage;
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            AllMemberIdsWindow.INSTANCE,
            AllBookIdsWindow.INSTANCE,
            AddNewLibraryMemberWindow.INSTANCE
    };

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    private LibrarySystem() {
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();

        createMenus();
        //pack();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        //for Windows file system
//    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
        //for unix file system
        pathToImage = currDirectory + "/src/librarysystem/library.jpg";

    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    private void addMenuItems() {
        options = new JMenu("Options");
        menuBar.add(options);
        login = new JMenuItem("Login");
        login.addActionListener(new LoginListener());
        allBookIds = new JMenuItem("All Book Ids");
        allBookIds.addActionListener(new AllBookIdsListener());
        allMemberIds = new JMenuItem("All Member Ids");
        allMemberIds.addActionListener(new AllMemberIdsListener());
        addNewMember = new JMenuItem("Add Library Member");
        addNewMember.addActionListener(new AddNewLibraryMember());
        options.add(login);
        options.add(allBookIds);
        options.add(allMemberIds);
        options.add(addNewMember);
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);

        }

    }

    class AllBookIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            //AllBookIdsWindow.INSTANCE.setData(sb.toString());
            //AllBookIdsWindow.INSTANCE.pack();
            //AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);

        }

    }

    class AllMemberIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllMemberIdsWindow.INSTANCE.init();
            //AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);


            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            //AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            //AllMemberIdsWindow.INSTANCE.pack();
            //AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);


        }

    }

    class AddNewLibraryMember implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AddNewLibraryMemberWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AddNewLibraryMemberWindow.INSTANCE);
            AddNewLibraryMemberWindow.INSTANCE.setVisible(true);

        }

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
