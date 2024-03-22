package game;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Body {
    public Player(int x, int y) {
        super(x, y);
    }

    
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(x * 20, y * 20, width, height);
    }
}
