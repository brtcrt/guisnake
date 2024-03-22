package gui;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.GameManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakePanel extends JPanel implements  ActionListener{
    protected GameManager manager;
    protected SnakeFrame frame;
    private Timer timer;
    
    public SnakePanel(SnakeFrame f) {
        frame = f;
        timer = new Timer(100, this);
        timer.start();
        manager = new GameManager(frame.x_bound, frame.y_bound);
        this.setPreferredSize(frame.getPreferredSize());
        this.setVisible(true);
    }

    @Override 
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(10, 10, 50, 10);
        if(!manager.drawNext(g)) {
            frame.fm.menuFrame = new MenuFrame(frame.fm, manager.getScore());
            frame.fm.snakeFrame.setVisible(false);
            frame.fm.menuFrame.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}
