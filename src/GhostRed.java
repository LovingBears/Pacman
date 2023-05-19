import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class GhostRed extends Enemy implements Moveable{
    private static final Image GHOSTIMG = new Image("res/ghostRed.png");
    private static final int GHOSTPIXELS = 25;
    private static final int DEFAULT_SPEED = 1;
    private double speed = DEFAULT_SPEED;
    private Point startingPosition;
    private boolean movingRight = true;

    /** Constructor
     *
     * @param point Drawn from top left
     */
    public GhostRed(Point point){
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

    /** Empty implementation to fulfill Movement interface
     *
     * @param walls
     */
    @Override
    public void moveDown(ArrayList<Wall> walls) {}

    /** Empty implementation to fulfill Movement interface
     *
     * @param walls
     */
    @Override
    public void moveUp(ArrayList<Wall> walls) {}

    /** Method that checks if a move is valid
     *
     * @param walls The walls
     * @param futurePosition The future move of an entity
     * @return boolean Returns if the move is possible or not
     */
    public boolean isLegalMove(ArrayList<Wall> walls, Entity futurePosition){
        for (Wall wall: walls){
            if (wall.isColliding(futurePosition)){
                movingRight = !movingRight;
                return false;
            }
        }
        return true;
    }

    /** Checks if this is moving right
     *
     * @return boolean
     */
    public boolean isMovingRight() {
        return movingRight;
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
