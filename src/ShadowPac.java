import bagel.*;
import bagel.util.*;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;


/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * Patrick Zhou
 */
public class ShadowPac extends AbstractGame  {

    //window settings
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    //constants for title
    private final static int DEFAULT_FONT_SIZE = 64;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static Point LOCATION_TITLE = new Point(260, 250);

    //constants for the level 1
    private final static int INSTRUCTIONS_SIZE = 24;
    private final static String INSTRUCTIONS_1 = "PRESS SPACE TO START";
    private final static Point LOCATION_INSTRUCTIONS_1 = new Point(320, 440);
    private final static String INSTRUCTIONS_2 = "USE ARROW KEYS TO MOVE";
    private final static Point LOCATION_INSTRUCTIONS_2 = new Point(305, 480);
    private final static String LOSS = "GAME OVER!";
    private final static String WIN_LEVEL1 = "LEVEL COMPLETE!";
    private static final int WIN_TIME = 300;

    //constants for level 2
    private static final int INSTRUCTIONS_SIZE_LEVEL2 = 40;
    private static final String INSTRUCTIONS_LEVEL2 = "EAT THE PELLET TO ATTACK";
    private static final Point LOCATION_INSTRUCTIONS_LEVEL2 = new Point(200, 350);
    private static final Point LOCATION_INSTRUCTIONS2_LEVEL2 = new Point(175, 400);
    private static final Point LOCATION_INSTRUCTIONS3_LEVEL2 = new Point(150, 450);
    private final static String WIN_LEVEL2 = "WELL DONE!";

    //game trackers
    private boolean gameStarted = false;
    private int inGameClock = 0;
    private boolean winLevelOne = false;
    private int levelOneRecord;
    private boolean startLevelTwo = false;
    private boolean winLevelTwo = false;
    private Level level;

    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String csvFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            String line;


            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                String name = values[0];
                Point position = new Point(Integer.parseInt(values[1]), Integer.parseInt(values[2]));

                //loads the player
                if (name.equals("Player")) {
                    level.setPlayer(new Player(position));
                }

                //loads the ghosts
                if (name.equals("Ghost")){
                    level.addEnemy(new GhostDefault(position));
                }
                if (name.equals("GhostRed")){
                    level.addEnemy(new GhostRed(position));
                }
                if (name.equals("GhostBlue")){
                    level.addEnemy(new GhostBlue(position));
                }
                if (name.equals("GhostGreen")){
                    level.addEnemy(new GhostGreen(position));
                }
                if (name.equals("GhostPink")){
                    level.addEnemy(new GhostPink(position));
                }

                //loads the walls
                if (name.equals("Wall")){
                    level.addWall(new Wall(position));
                }

                //loads the edibles
                if (name.equals("Dot")){
                    level.addDot(new Dot(position));
                }
                if (name.equals("Cherry")){
                    level.addCherry(new Cherry(position));
                }
                if (name.equals("Pellet")){
                    level.addPellet(new Pellet(position));
                }


            }

            } catch (Exception e) {
            e.printStackTrace();
            }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        inGameClock++;
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        //checks the player input
        if (input.isDown(Keys.LEFT)){
            level.getPlayer().moveLeft(level.getWalls());
        }
        if (input.isDown(Keys.UP)){
            level.getPlayer().moveUp(level.getWalls());
        }
        if (input.isDown(Keys.RIGHT)){
            level.getPlayer().moveRight(level.getWalls());
        }
        if (input.isDown(Keys.DOWN)){
            level.getPlayer().moveDown(level.getWalls());
        }
        if (input.wasPressed(Keys.W)){
            winLevelOne = true;
            levelOneRecord = inGameClock;
        }

        //while game has not started
        if (!(input.wasPressed(Keys.SPACE)) && !gameStarted){
            printMessage(GAME_TITLE, DEFAULT_FONT_SIZE, LOCATION_TITLE);
            printMessage(INSTRUCTIONS_1, INSTRUCTIONS_SIZE, LOCATION_INSTRUCTIONS_1);
            printMessage(INSTRUCTIONS_2, INSTRUCTIONS_SIZE, LOCATION_INSTRUCTIONS_2);
            level = new Level1();
            readCSV("res/level0.csv");
        }

        //when level 1 is complete
        if (level.levelWin() && !winLevelOne){
            winLevelOne = true;
            levelOneRecord = inGameClock;
        }

        //transition screen
        if (winLevelOne && !startLevelTwo){
            if (inGameClock - levelOneRecord < WIN_TIME){
                printMessageCentre(WIN_LEVEL1);
            }
            else if (input.wasPressed(Keys.SPACE)){
                startLevelTwo = true;
            }
            else {
                level = new Level2();
                readCSV("res/level1.csv");
                printMessage(INSTRUCTIONS_1, INSTRUCTIONS_SIZE_LEVEL2, LOCATION_INSTRUCTIONS_LEVEL2);
                printMessage(INSTRUCTIONS_2, INSTRUCTIONS_SIZE_LEVEL2, LOCATION_INSTRUCTIONS2_LEVEL2);
                printMessage(INSTRUCTIONS_LEVEL2, INSTRUCTIONS_SIZE_LEVEL2, LOCATION_INSTRUCTIONS3_LEVEL2);
            }
        }

        //while game is won
        else if (level.levelWin() && winLevelOne){
            winLevelTwo = true;
            printMessageCentre(WIN_LEVEL2);
        }

        //while game is lost
        else if (level.levelLost()) {
            printMessageCentre(LOSS);
        }

        //processes the game when started
        else {
            gameStarted = true;
            level.drawLevel(inGameClock);
            if (level instanceof Level2){
                ((Level2) level).moveGhosts();
            }
        }
    }

    /** Prints a message to the screen
     *
     * @param msg The message
     * @param size The size of the message
     * @param point The top left point from where the message should be drawn
     */
    public void printMessage(String msg, int size, Point point){
        Font font = new Font("res/FSO8BITR.TTF", size);
        font.drawString(msg, point.x, point.y);
    }

    /** Prints a message with the default 64 size to the centre of the screen
     *
     * @param msg The message
     */
    public void printMessageCentre(String msg){
        Font font = new Font("res/FSO8BITR.TTF", DEFAULT_FONT_SIZE);
        double centreWidth = (WINDOW_WIDTH-font.getWidth(msg))/2.0;
        double centreHeight = WINDOW_HEIGHT/2.0;
        font.drawString(msg, centreWidth, centreHeight);
    }

}
