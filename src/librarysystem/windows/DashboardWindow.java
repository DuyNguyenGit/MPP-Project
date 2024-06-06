package librarysystem.windows;

import business.SystemController;

import javax.swing.*;
import java.awt.*;

public class DashboardWindow extends JPanel {
    public static final DashboardWindow INSTANCE = new DashboardWindow();
    SystemController ci = new SystemController();

    private JPanel mainPanel, statitsiquePanel, booksCounterPanel, memberCounterPanel, recordCounterPanel,
            profilePicturePanel;
    private JLabel booksCounterLabel, memberCounterLabel, recordCounterLabel;

    private DashboardWindow() {
        super(new CardLayout());
        init();
    }

    public void init() {
        setProfilePicture();

    }

    private void setProfilePicture() {
        String currDirectory = System.getProperty("user.dir");
        String pathToImage = currDirectory + "/src/librarysystem/home.jpg";
        ImageIcon image = new ImageIcon(pathToImage);

        JLabel icon = new JLabel(image);
        add(icon, BorderLayout.CENTER);

    }

}
