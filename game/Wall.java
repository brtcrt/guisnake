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
        g.fillRect(x * 20, y * 20, width, height);
        // if (type.equals("-")) {
        //     g.drawLine(x, y + offset, x + width, y);
        // } else {
        //     g.drawLine(x + offset, y, x, y + height);
        // }
    }
    
}
