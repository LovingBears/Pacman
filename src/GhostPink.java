import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;

public class GhostPink extends Enemy implements Moveable{
    private static final Image GHOSTIMG = new Image("res/ghostPink.png");
    private static final int GHOSTPIXELS = 25;
    private static final int DEFAULT_SPEED = 3;
    private static final int TOTAL_DIRECTIONS = 4;
    private double speed = DEFAULT_SPEED;
    Random random = new Random();
    private int direction =  randomDirection();
    private Point startingPosition;


    /** Constructor
     *
     * @param point Drawn from top left
     */
    public GhostPink(Point point){
        super(point, GHOSTIMG, GHOSTPIXELS);
        this.startingPosition = point;
    }

    /** Leftwards movement
     *
     * @param walls The walls that need to considered for a valid move
     */
    public void moveLeft(ArrayList<Wall> walls){
        Point newPosition = new Point(getPoint().x - speed, getPoint().y);
        GhostRed futurePosition = new GhostRed(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }

    /** Rightwards movement
     *
     * @param walls The walls that need to considered for a valid move
     */
    public void moveRight(ArrayList<Wall> walls){
        Point newPosition = new Point(getPoint().x + speed, getPoint().y);
        GhostRed futurePosition = new GhostRed(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }

    /** Downwards movement
     *
     * @param walls The walls that need to considered for a valid move
     */
    public void moveDown(ArrayList<Wall> walls){
        Point newPosition = new Point(getPoint().x, getPoint().y + speed);
        GhostBlue futurePosition = new GhostBlue(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }

    /** Upwards movement
     *
     * @param walls The walls that need to considered for a valid move
     */
    public void moveUp(ArrayList<Wall> walls){
        Point newPosition = new Point(getPoint().x, getPoint().y - speed);
        GhostBlue futurePosition = new GhostBlue(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }

    /** Method that checks if a move is valid
     *
     * @param walls The walls
     * @param futurePosition The future move of an entity
     * @return boolean Returns if the move is possible or not
     */
    public boolean isLegalMove(ArrayList<Wall> walls, Entity futurePosition){
        for (Wall wall: walls){
            if (wall.isColliding(futurePosition)){
                direction = randomDirection();
                return false;
            }
        }
        return true;
    }

    /** Returns this ghost to its spawning point
     *
     */
    public void returnToStart(){
        this.setPoint(startingPosition);
    }

    /** Method that chooses a random direction
     *
     * @return int The direction in terms of an int
     */
    public int randomDirection(){
        return random.nextInt(TOTAL_DIRECTIONS);
    }

    /** Getter for the direction
     *
     * @return int The direction in terms of an int
     */
    public int getDirection() {
        return direction;
    }

    /** Begins frenzy behaviour
     *
     */
    public void startFrenzy(){
        this.speed -= FRENZY_SPEED;
        this.setSprite(FRENZYIMG);
    }

    /** Stops frenzy behaviour
     *
     */
    public void stopFrenzy(){
        this.speed += FRENZY_SPEED;
        this.setSprite(GHOSTIMG);
    }

    /** Getter for spawning point
     *
     * @return Point The starting point
     */
    public Point getStartingPosition() {
        return startingPosition;
    }
}
