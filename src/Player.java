import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class Player extends Entity implements Moveable{
    private static final Image PLAYERIMG = new Image("res/pac.png");
    private static final Image PLAYERIMG2 = new Image("res/pacOpen.png");
    private static final int PLAYERPIXELS = 25;
    private DrawOptions options = new DrawOptions();
    private static final int INITIAL_LIVES = 3;
    private static final int INITIAL_SCORE = 0;
    private final static int OPEN_MOUTH = 15;
    private static final int DEFAULT_SPEED = 3;
    private static final int FRENZY_SPEED = 4;
    private double direction;
    private boolean mouthOpen;
    private int speed = DEFAULT_SPEED;
    private final Point resetPosition;
    private int lives;
    private int score;

    /** Constructor for player
     *
     * @param point The initial position of the player
     */
    public Player(Point point){
        super(point, PLAYERIMG, PLAYERPIXELS);
        this.resetPosition = point;
        this.direction = RIGHT;
        this.mouthOpen = false;
        this.lives = INITIAL_LIVES;
        this.score = INITIAL_SCORE;
    }

    /** This method opens/closes the mouth every 15 frames
     *
     * @param currentFrame The current frame of the game based on the ShadowPac in game clock
     */
    public void switchSprite(int currentFrame){
        if (currentFrame % OPEN_MOUTH == 0){
            if (mouthOpen){
                this.setSprite(PLAYERIMG);
                mouthOpen = false;
            }
            else {
                this.setSprite(PLAYERIMG2);
                mouthOpen = true;
            }
        }
    }

    /** An overridden draw method that can use the draw options to rotate the sprite
     *
     */
    @Override
    public void draw(){
        this.getSprite().drawFromTopLeft(getPoint().x, getPoint().y, options.setRotation(direction));
    }

    /** These 4 methods move the player when given the walls
     *
     * @param walls An array list of the walls
     */
    public void moveLeft(ArrayList<Wall> walls){
        this.direction = LEFT;
        Point newPosition = new Point(getPoint().x - speed, getPoint().y);
        Player futurePosition = new Player(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }
    public void moveRight(ArrayList<Wall> walls){
        this.direction = RIGHT;
        Point newPosition = new Point(getPoint().x + speed, getPoint().y);
        Player futurePosition = new Player(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }
    public void moveDown(ArrayList<Wall> walls){
        this.direction = DOWN;
        Point newPosition = new Point(getPoint().x, getPoint().y + speed);
        Player futurePosition = new Player(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }
    public void moveUp(ArrayList<Wall> walls){
        this.direction = UP;
        Point newPosition = new Point(getPoint().x, getPoint().y - speed);
        Player futurePosition = new Player(newPosition);
        if (isLegalMove(walls, futurePosition)){
            this.setPoint(newPosition);
            this.moveRectangle(newPosition);
        }
    }

    /** This method checks if a move won't clip into the wall
     *
     * @param walls An array list of the walls
     * @param futurePosition An entity of a given position
     * @return boolean Returns if the move is possible or not
     */
    public boolean isLegalMove(ArrayList<Wall> walls, Entity futurePosition){
        for (Wall wall: walls){
            if (wall.isColliding(futurePosition)){
                return false;
            }
        }
        return true;
    }

    /** This method represents the behaviour for colliding with enemies and losing lives
     *
     * @param enemies The list of enemies
     * @return boolean Returns true if a collision is happening this frame
     */
    public boolean isCollidingEnemy(ArrayList<Enemy> enemies){
        for (Enemy enemy: enemies){
            if (this.isColliding(enemy)){
                this.lives--;
                this.setPoint(resetPosition);
                return true;
            }
        }
        return false;
    }

    /** This method represents the behaviour for with a single enemy and gaining score during frenzy
     *
     * @param enemy The given enemy
     * @param frenzyOn This is just a boolean value to overload the above method to separate the behaviours
     * @return Boolean Returns true if a collision is happening this frame
     */
    public boolean isCollidingEnemy(Enemy enemy, boolean frenzyOn){
        if (this.isColliding(enemy)){
            this.score += 30;
            return true;
        }
        return false;
    }

    /** This method represents the behaviour for collision with a single enemy and losing lives
     *
     * @param enemy The given enemy
     * @return Boolean Returns true if a collision is happening this frame
     */
    public boolean isCollidingEnemy(Enemy enemy){
        if (this.isColliding(enemy)){
            this.lives--;
            this.setPoint(resetPosition);
            this.moveRectangle(resetPosition);
            return true;
        }
        return false;
    }

    /** This method initiates the frenzy behaviour for the player
     *
     */
    public void startFrenzy(){
        this.speed = FRENZY_SPEED;
    }

    /** This method terminates the frenzy behaviour for the player
     *
     */
    public void stopFrenzy() {
        this.speed = DEFAULT_SPEED;
    }

    /** This method adds to the players score
     *
     * @param dotScore The given amount of score
     */
    public void addScore(int dotScore){
        this.score += dotScore;
    }

    /**
     *
     * @return int Returns the current lives
     */
    public int getLives() {
        return lives;
    }

    /**
     *
     * @returns int Returns the current score
     */
    public int getScore() {
        return score;
    }
}
