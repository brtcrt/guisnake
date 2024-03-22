package game;

import java.awt.Graphics;

public abstract class GameObject {
    public int x;
    public int y;
    protected int width = 20;
    protected int height = 20;
    public abstract void draw(Graphics g);
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
