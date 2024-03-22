package game;

import java.awt.Color;
import java.awt.Graphics;

public class Food extends GameObject {
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw (Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x * 20, y * 20, width, height);
    }
    
}