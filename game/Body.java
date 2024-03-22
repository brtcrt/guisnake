package game;

import java.awt.Color;
import java.awt.Graphics;

public class Body extends GameObject {
    private Body ahead;
    private Body behind;

    public Body(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Body(Body ahead) {
        this.ahead = ahead;
        this.x = ahead.x;
        this.y = ahead.y;
        ahead.setBehind(this);
    }

    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x * 20, y * 20, width, height);
    }

    public Body getAhead() {
        return ahead;
    }

    public Body getBehind() {
        return behind;
    }

    public void setAhead(Body ahead) {
        this.ahead = ahead;
    }

    public void setBehind(Body behind) {
        this.behind = behind;
    }
    
}
