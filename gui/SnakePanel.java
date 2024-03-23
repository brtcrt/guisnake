package gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.GameManager;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakePanel extends JPanel implements  ActionListener{
    protected GameManager manager;
    protected SnakeFrame frame;
    private Timer timer;
    
    public SnakePanel(SnakeFrame f) {
        frame = f;
        timer = new Timer(100 - f.difficulty * 20, this);
        timer.start();
        manager = new GameManager(frame.x_bound, frame.y_bound);
        this.setPreferredSize(frame.getPreferredSize());
        this.setVisible(true);
    }

    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!manager.drawNext(g)) {
            frame.fm.snakeFrame.setVisible(false);
            int option = JOptionPane.showConfirmDialog(this, "Score: " + manager.getScore() + ", do you want to play again?", "Game over!", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                frame.fm.menuFrame = new MenuFrame(frame.fm, manager.getScore());
                frame.fm.menuFrame.setVisible(true);
            } else {
                System.exit(0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
     
}
