package gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MenuFrame extends JFrame {
    public FrameManager fm;
    public MenuPanel panel;
    public MenuFrame(FrameManager fm, int prev) {
        this.fm = fm;
        panel = new MenuPanel(this, prev);
        this.setBounds(100, 100, 300, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().add(panel);
    }
    
}
