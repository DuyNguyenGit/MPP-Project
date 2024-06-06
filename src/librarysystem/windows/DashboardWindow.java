package librarysystem.windows;

import librarysystem.LibWindow;

import javax.swing.*;
import java.awt.*;

public class DashboardWindow extends JPanel implements LibWindow {
    public static final DashboardWindow INSTANCE = new DashboardWindow();
    private boolean isInitialized = false;

    private DashboardWindow() {
        super(new CardLayout());
        init();
    }

    @Override
    public void init() {
        String currDirectory = System.getProperty("user.dir");
        String pathToImage = currDirectory + "/src/librarysystem/library.jpg";
        ImageIcon image = new ImageIcon(pathToImage);
        JLabel icon = new JLabel(image);
        add(icon, BorderLayout.CENTER);
        isInitialized(true);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }
}
