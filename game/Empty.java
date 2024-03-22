package game;

import java.awt.Color;
import java.awt.Graphics;

public class Empty extends GameObject {
    public Empty(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x * 20, y * 20, width, height);
    }
    
}
