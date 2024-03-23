package game;

import java.awt.Color;
import java.awt.Graphics;

public class Wall extends GameObject {
    private String type;
    private int offset;
    public Wall(int x, int y, String s, int offset) {
        this.x = x;
        this.y = y;
        this.type = s;
        this.offset = offset;
    }   

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        if (type.equals("-")) {
            g.drawLine(x * 20, y * 20 + offset, x * 20 + width, y * 20 + offset);
        } else if(type.equals("|")) {
            g.drawLine(x * 20 + offset, y * 20, x * 20 + offset, y * 20 + height);
        } else {
            g.drawLine(x * 20, y * 20, x * 20, y * 20);
        }
    }
    
}
