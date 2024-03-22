package gui;

public class FrameManager {
    protected SnakeFrame snakeFrame;
    protected MenuFrame menuFrame;
    public FrameManager() {
        menuFrame = new MenuFrame(this, -1);
        menuFrame.setVisible(true);
    }

}
