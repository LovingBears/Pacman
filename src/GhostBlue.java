import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class GhostBlue extends Enemy implements Moveable{
    private static final Image GHOSTIMG = new Image("res/ghostBlue.png");
    private static final int GHOSTPIXELS = 25;
    private static final int DEFAULT_SPEED = 2;
    private double speed = DEFAULT_SPEED;
    private Point startingPosition;
    private boolean movingDown = true;


    /** Constructor
     *
     * @param point Drawn from top left
     */
    public GhostBlue(Point point){
        super(point, GHOSTIMG, GHOSTPIXELS);
        this.startingPosition = point;
    }

    /** Empty implementation to fulfill Movement interface
     *
     * @param walls
     */
    public void moveLeft(ArrayList<Wall> walls){}

    /** Empty implementation to fulfill Movement interface
     *
     * @param walls
     */
    public void moveRight(ArrayList<Wall> walls){}

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
                movingDown = !movingDown;
                return false;
            }
        }
        return true;
    }

    /** Checks if this is moving down
     *
     * @return boolean
     */
    public boolean isMovingDown() {
        return movingDown;
    }

    /** Returns this ghost to its spawning point
     *
     */
    public void returnToStart(){
        this.setPoint(startingPosition);
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
