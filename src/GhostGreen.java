import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Random;

public class GhostGreen extends Enemy implements Moveable{
    private static final Image GHOSTIMG = new Image("res/ghostGreen.png");
    private static final int GHOSTPIXELS = 25;
    private static final int DEFAULT_SPEED = 4;
    private double speed = DEFAULT_SPEED;
    private Point startingPosition;
    Random random = new Random();
    private boolean horizontal = random.nextBoolean();
    private boolean movingDirection = true;


    /** Constructor
     *
     * @param point Drawn from top left
     */
    public GhostGreen(Point point){
        super(point, GHOSTIMG, GHOSTPIXELS);
        this.startingPosition = point;
    }

    /** Leftwards movement
     *
     * @param walls The walls that need to considered for a valid move
     */
    public void moveLeft(ArrayList<Wall> walls){
        Point newPosition = new Point(getPoint().x - speed, getPoint().y);
        GhostGreen futurePosition = new GhostGreen(newPosition);
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
        GhostGreen futurePosition = new GhostGreen(newPosition);
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
        GhostGreen futurePosition = new GhostGreen(newPosition);
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
        GhostGreen futurePosition = new GhostGreen(newPosition);
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
                movingDirection = !movingDirection;
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

    /** Checks which way this ghost will move until eaten
     *
     * @return boolean Returns true if horizontal, false for vertical
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /** Checks if this is moving in the default direction
     * (Right for vertical, Down for horizontal)
     * @return boolean Returns true if moving in the default direction
     */
    public boolean isMovingDirection() {
        return movingDirection;
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
