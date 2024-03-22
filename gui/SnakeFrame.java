package gui;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class SnakeFrame extends JFrame implements KeyListener {
    public final int x_bound;
    public final int y_bound;
    public SnakePanel panel;
    public FrameManager fm;
    public SnakeFrame(FrameManager fm, int x, int y) {
        this.fm = fm;
        x_bound = x;
        y_bound = y;
        this.setBounds(calculateDimensions());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        panel = new SnakePanel(this);
        this.add(panel);
        this.addKeyListener(this);
    }

    private Rectangle calculateDimensions() {
        return new Rectangle(100, 100, x_bound * 20 + 15, y_bound * 20 + 35);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        String dir = "" + e.getKeyChar();
        panel.manager.setDir(dir);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
