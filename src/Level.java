import bagel.Font;
import bagel.Image;
import bagel.util.Point;

import java.util.*;

public abstract class Level {

    //constants for hearts
    private final static Point HEART_LOCATION = new Point(900,10);
    private final static int HEART_GAP = 30;
    private final Image HEART_IMAGE = new Image("res/heart.png");

    //constants for score
    private final static String SCORE = "SCORE ";
    private final static int SCORE_SIZE = 20;
    private final static Point SCORE_LOCATION = new Point(25,25);

    //level objects
    private Player player;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Wall> walls = new ArrayList<Wall>();


    /** Method that checks if the level is won
     *
     * @return boolean Returns true if successfully completed level
     */
    public abstract boolean levelWin();

    /** Method that checks if the level is lost
     *
     * @return boolean Returns true is all hearts lost
     */
    public boolean levelLost(){
        if (player.getLives() == 0){
            return true;
        }
        return false;
    }

    /** Methods that draw all the appropriate objects in the level
     *
     * @param inGameClock Given frame counter to calculate when to do certain behaviours
     */
    public abstract void drawLevel(int inGameClock);

    /** draws the player
     *
     */
    public void drawPlayer(){
        player.draw();
    }

    /** draws the walls
     *
     */
    public void drawWalls(){
        for (Wall wall: walls){
            wall.draw();
        }
    }

    /** draws and deletes the dots when eaten
     *
     */
    public void drawDots(){
        Dot eatenDot = null;
        for (Dot dot: dots){
            if (dot.isCollidingPlayer(player)){
                player.addScore(dot.SCORE);
                eatenDot = dot;
            }
            else {
                dot.draw();
            }
        }
        dots.remove(eatenDot);
    }

    /** draws the hearts
     *
     */
    public void drawHearts(){
        for (int i=0;i<player.getLives();i++){
            HEART_IMAGE.drawFromTopLeft(((HEART_LOCATION.x)+(i*HEART_GAP)), HEART_LOCATION.y);
        }
    }

    /** draws the score
     *
     */
    public void drawScore(){
        Font font = new Font("res/FSO8BITR.TTF", SCORE_SIZE);
        font.drawString(SCORE + player.getScore(), SCORE_LOCATION.x, SCORE_LOCATION.y);
    }

    /** Getter for player
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /** Setter for player
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /** Getter for walls
     *
     * @return walls
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    /** Getter for dots
     *
     * @return dots
     */
    public ArrayList<Dot> getDots() {
        return dots;
    }

    /** Adds a wall to walls
     *
     * @param wall
     */
    public void addWall(Wall wall){
        walls.add(wall);
    }

    /** Adds a dot to dots
     *
     * @param dot
     */
    public void addDot(Dot dot){
        dots.add(dot);
    }

    /** Unimplemented methods required to call level 2 methods from the superclass
     *
     * @param cherry
     */
    public void addCherry(Cherry cherry){}

    /** Unimplemented methods required to call level 2 methods from the superclass
     *
     * @param pellet
     */
    public void addPellet(Pellet pellet){}

    /** Unimplemented methods required to call level 2 methods from the superclass
     *
     * @param enemy
     */
    public abstract void addEnemy(Enemy enemy);
}
