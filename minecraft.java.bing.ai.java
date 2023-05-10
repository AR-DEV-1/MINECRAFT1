import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MiniMinecraft extends JFrame implements KeyListener {

    // constants for window size and block size
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int BLOCK_SIZE = 20;

    // constants for terrain types
    public static final int AIR = 0;
    public static final int GRASS = 1;
    public static final int DIRT = 2;
    public static final int STONE = 3;

    // colors for terrain types
    public static final Color[] COLORS = {Color.WHITE, Color.GREEN, Color.BROWN, Color.GRAY};

    // 2D array to store the terrain
    private int[][] terrain;

    // variables to store the player position and direction
    private int playerX;
    private int playerY;
    private int playerDir;

    // constructor
    public MiniMinecraft() {
        // set up the window
        super("Mini Minecraft");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);

        // initialize the terrain
        terrain = new int[WIDTH / BLOCK_SIZE][HEIGHT / BLOCK_SIZE];
        generateTerrain();

        // initialize the player position and direction
        playerX = WIDTH / 2;
        playerY = HEIGHT / 2;
        playerDir = 0;
    }

    // method to generate random terrain
    private void generateTerrain() {
        // loop through the rows of the terrain array
        for (int i = 0; i < terrain.length; i++) {
            // loop through the columns of the terrain array
            for (int j = 0; j < terrain[i].length; j++) {
                // generate a random number between 0 and 99
                int r = (int) (Math.random() * 100);
                // if the random number is less than 10, set the terrain to air
                if (r < 10) {
                    terrain[i][j] = AIR;
                }
                // else if the random number is less than 40, set the terrain to grass
                else if (r < 40) {
                    terrain[i][j] = GRASS;
                }
                // else if the random number is less than 70, set the terrain to dirt
                else if (r < 70) {
                    terrain[i][j] = DIRT;
                }
                // else set the terrain to stone
                else {
                    terrain[i][j] = STONE;
                }
            }
        }
    }

    // method to paint the graphics
    public void paint(Graphics g) {
        // clear the window
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // loop through the rows of the terrain array
        for (int i = 0; i < terrain.length; i++) {
            // loop through the columns of the terrain array
            for (int j = 0; j < terrain[i].length; j++) {
                // get the terrain type at this position
                int t = terrain[i][j];
                // get the color for this terrain type
                Color c = COLORS[t];
                // set the graphics color to this color
                g.setColor(c);
                // draw a rectangle at this position with the block size
                g.fillRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        // draw the player as a black circle
        g.setColor(Color.BLACK);
        g.fillOval(playerX - BLOCK_SIZE / 2, playerY - BLOCK_SIZE / 2, BLOCK_SIZE, BLOCK_SIZE);

        // draw a line in front of the player to indicate the direction
        g.drawLine(playerX, playerY, playerX + (int) (Math.cos(playerDir) * BLOCK_SIZE), playerY + (int) (Math.sin(playerDir) * BLOCK_SIZE));
    }

    // method to handle key pressed events
    public void keyPressed(KeyEvent e) {
        // get the key code of the pressed key
        int keyCode = e.getKeyCode();
        // if the key is W, move forward in the current direction
        if (keyCode == KeyEvent.VK_W) {
            moveForward();
        }
        // if the key is A, turn left by 15 degrees
