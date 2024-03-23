/**
 * @author brtcrt
 */
package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Game manager class for the gui snake game. Basically a modified version of the cmd snake game.
 * Cleaner implementation with inheritance.
 */
public class GameManager {
    // Instance variables
    private final int x_max;
    private final int y_max;
    private String dir;
    private boolean hasFood;
    private int score;
    private boolean isDead;
    private boolean addBody;
    private Player player;
    private Food food;
    private GameObject[][] board; // A matrix of GameObjects to keep track of the game.

    /**
     * Creates the GameManager object.
     * @param x_bound int
     * @param y_bound int
     */
    public GameManager(int x_bound, int y_bound) {
        this.x_max = x_bound;
        this.y_max = y_bound;
        this.hasFood = false;
        this.score = 0;
        this.isDead = false;
        this.addBody = false;
        this.dir = "d"; // moves left at start
        this.board = new GameObject[y_bound][x_bound];
        this.startup();
        
    }
    
    /**
     * Sets up the game board.
     */
    private void startup() {
        this.generateAllEmpty();
        this.generateWalls();
        this.generatePlayer();
        this.addFood();
    }


    /**
     * Main game loop. Runs until the player is dead.
     */
    public boolean drawNext(Graphics g) {
        if (!this.isDead) {
            this.draw(g);
            this.nextStep();
            return true;
        } 
        return false;
    }

    public int getScore() {
        return score;
    }

    public void setDir(String direction) {
        if (direction.equals("w") || direction.equals("a") || direction.equals("s") || direction.equals("d")) {
            if (!((this.dir.equals("w") && direction.equals("s") || 
            this.dir.equals("s") && direction.equals("w")) || 
            (this.dir.equals("a") && direction.equals("d") || 
            this.dir.equals("d") && direction.equals("a")))) {
                this.dir = direction;
            }
        }
    }

    /**
     * Draws the game to the screen using the given Graphics object.
     * The Graphics provided is from the {@link SnakePanel}
     * Every GameObject has a draw method.
     */
    private void draw(Graphics g) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                GameObject o = this.board[i][j];
                o.draw(g);
            }
        }
        g.setColor(Color.RED);
        g.drawString("Score: " + this.score, 0, 15);
    }
    
    /**
     * What goes inside the game loop itself.
     * Get input
     * Calculate if we die from that input
     * If not make the move
     * Refresh the board
     */
    private void nextStep() {

        this.isDead = this.calculateDeath(dir);
        if (this.isDead) return; // No need to move the player if we die.

        if (this.eatFood(dir)) {
            this.hasFood = false;
            this.addBody = true;
            this.score++;

        }

        this.movePlayer(dir);

        this.refreshBoard();
    }

    /**
     * After every move of the player, the board is updated accordingly.
     * The {@link Food} and the {@link Player} is placed first.
     * After that, the body parts of the player are inserted by getting the GameObject
     * that is linked to the player and the one linked to that and so on.
     */
    private void refreshBoard() {
        this.generateAllEmpty();
        this.generateWalls();
        /*
         * HOW THE LINKED STRUCTURE WORKS:
         * Player has several body parts, but only knows the one right before it (or after it depending on the perspective)
         * This works as every other body GameObject also know the next GameObject that comes before and after it.
         * Player-Body1-Body2-Body3-Body4-...
         * Player is connected to Body1
         * Body1 is connected to Body2
         * and so on.
         */
        Body nextObj = this.player; // since the Player class is a Body
        while (nextObj != null) { // Until there are no more body parts.
            this.board[nextObj.y][nextObj.x] = nextObj;
            nextObj = nextObj.getBehind();
        }
        if (!this.hasFood) {
            addFood();
        } else {
            this.board[food.y][food.x] = this.food;
        }
        
    }

    /**
     * First, fill the board with Empty objects.
     */
    private void generateAllEmpty() {
        for (int i = 0; i < this.y_max; i++) {
            for (int j = 0; j < this.x_max; j++) {
                this.board[i][j] = new Empty(j, i);
            }
        }
    }

    /**
     * Generate the walls.
     */
    private void generateWalls() {
        for (int i = 0; i < this.y_max; i++) {
            for (int j = 0; j < this.x_max; j++) {
                if ((j == 0 && i == 0) || (j == x_max - 1 && i == 0) || (j == 0 && i == y_max - 1) || (j == x_max - 1 && i == y_max - 1)) {
                    this.board[i][j] = new Wall(j, i, "+", 19);
                } else if ((i == 0 || i == y_max - 1)) {
                    // Up and Down
                    if (i == 0) {
                        this.board[i][j] = new Wall(j, i, "-", 19);                  
                    } else if (i == y_max - 1){
                        this.board[i][j] = new Wall(j, i, "-", 0);
                    }
                } else if ((j == 0 || j == x_max - 1)) {
                    // Left and Right
                    if (j == 0) {
                        this.board[i][j] = new Wall(j, i, "|", 19);
                    } else {
                        this.board[i][j] = new Wall(j, i, "|", 0);
                    }
                }
            }
        }
    }
    
    /**
     * Generate the player initially.
     */
    private void generatePlayer() {
        int startX = this.x_max / 5;
        int startY = this.y_max / 2;
        this.player = new Player(startX, startY);
        this.board[startY][startX] = this.player;
    }

    /**
     * Insert a food GameObject at an empty spot.
     */
    private void addFood() {
        while (!this.hasFood) { // Until we have successfuly added the food.
            int randX = (int)(x_max * Math.random()); 
            int randY = (int)(y_max * Math.random()); 
            if (Empty.class.isInstance(this.board[randY][randX])) { // If the GameObject is an Empty instance
                this.food = new Food(randX, randY);
                this.board[randY][randX] = this.food;
                this.hasFood = true;
            }
        }
    }


    private boolean calculateDeath(String dir) {
        // If the GameObject at the direction of the Player is a Wall or Body, the game should end.
        if (dir.equals("w")) {
            GameObject up = this.getUp();
            if (Wall.class.isInstance(up) || Body.class.isInstance(up)) return true; 
        } else if (dir.equals("a")) {
            GameObject left = this.getLeft();
            if (Wall.class.isInstance(left) || Body.class.isInstance(left)) return true;
        } else if (dir.equals("s")) {
            GameObject down = this.getDown();
            if (Wall.class.isInstance(down) || Body.class.isInstance(down)) return true;
        } else {
            GameObject right = this.getRight();
            if (Wall.class.isInstance(right) || Body.class.isInstance(right)) return true;
        }
        return false;
    }

    /**
     * @return The GameObject that is above the player.
     */
    private GameObject getUp() {
        return this.board[this.player.y - 1][this.player.x];
    }

    /**
     * @return The GameObject that is below the player.
     */
    private GameObject getDown() {
        return this.board[this.player.y + 1][this.player.x];
    }

    /**
     * @return The GameObject that is to the left of the player.
     */
    private GameObject getLeft() {
        return this.board[this.player.y][this.player.x - 1];
    }

    /**
     * @return The GameObject that is to the right of the player.
     */
    private GameObject getRight() {
        return this.board[this.player.y][this.player.x + 1];
    }

    /**
     * Moves the player using the direction provided.
     * @param dir String ("w", "s", "a", "d")
     */
    private void movePlayer(String dir) {
        // We need to store the initial coords of the player, as the body part
        // that comes after it will need to be placed there.
        int oldX = this.player.x;
        int oldY = this.player.y;
        if (dir.equals("w")) {
            this.player.y--;
        } else if (dir.equals("a")) {
            this.player.x--;
        } else if (dir.equals("s")) {
            this.player.y++;
        } else {
            this.player.x++;
        }
        this.moveBody(oldX, oldY);
    }

    /**
     * Moves the body of the player. Kill me.
     * @param oldX int
     * @param oldY int
     */
    private void moveBody(int oldX, int oldY) {
        /*
         * HOW THIS CLUSTERFUCK WORKS
         * 1) Get the initial coords of the player.
         * 2) Also get the initial coords of the body player behind it.
         * 3) Move the body part(1) to the coords of the player.
         * 4) Get the coords of the next body part(2).
         * 5) Move the body part(2) to body part(1)'s coords.
         * 6) Repeat the same thing until out of body parts.
         * Pain
         */
        Body obj = this.player.getBehind();
        Body last = this.player;
        int olderX = 0; // in case obj is null,
        int olderY = 0; // it won't have x or y
        while (obj != null) {
            olderX = obj.x;
            olderY = obj.y;
            obj.move(oldX, oldY);
            oldX = olderX;
            oldY = olderY;
            if (obj.getBehind() == null) {
                last = obj;
            }
            obj = obj.getBehind();
        }
        // Add a body part if we need to.
        if (this.addBody) {
            Body newObj = new Body(last);
            newObj.move(oldX, oldY);
            this.addBody = false;
        }
    }

    /**
     * Returns true if we have eaten the food. False otherwise.
     * @param dir String
     * @return boolean
     */
    private boolean eatFood(String dir) {
        if (dir.equals("w")) {
            GameObject up = this.getUp();
            if (Food.class.isInstance(up)) return true;
        } else if (dir.equals("a")) {
            GameObject left = this.getLeft();
            if (Food.class.isInstance(left)) return true;
        } else if (dir.equals("s")) {
            GameObject down = this.getDown();
            if (Food.class.isInstance(down)) return true;
        } else {
            GameObject right = this.getRight();
            if (Food.class.isInstance(right)) return true;
        }
        return false;
    }

}
